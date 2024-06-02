package com.example.androidboilerplate.screens

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.androidboilerplate.R
import com.example.androidboilerplate.core.BaseActivity
import com.example.androidboilerplate.databinding.ActivityHomeBinding
import com.example.androidboilerplate.utils.Logger
import com.example.androidboilerplate.utils.PrefManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    @Inject
    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        getPrefValue()
        setContentView(binding.root)
    }

    private fun getPrefValue() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        lifecycleScope.launch(Dispatchers.Main) {
            prefManager.getValue("isLogin", false).collectLatest {
                Logger.d("isAlreadyLogin: $it")
                if (it) {
                    graph.setStartDestination(R.id.homeFragment)
                } else {
                    graph.setStartDestination(R.id.loginFragment)
                }
                val navController = navHostFragment.navController
                navController.setGraph(graph, intent.extras)
            }
        }
    }

}