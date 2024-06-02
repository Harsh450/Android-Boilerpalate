package com.example.androidboilerplate.screens.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.getColor
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidboilerplate.R
import com.example.androidboilerplate.core.BaseFragment
import com.example.androidboilerplate.data.network.baseModel.BaseApiResponseModel
import com.example.androidboilerplate.databinding.FragmentHomeBinding
import com.example.androidboilerplate.screens.home.adapter.HomeAdapter
import com.example.androidboilerplate.screens.home.model.local.User
import com.example.androidboilerplate.screens.home.viewmodel.HomeViewModel
import com.example.androidboilerplate.utils.Logger
import com.example.androidboilerplate.utils.PrefManager
import com.example.androidboilerplate.utils.showDialog
import com.example.androidboilerplate.utils.showLongToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var homeAdapter: HomeAdapter
    @Inject
    lateinit var prefManager: PrefManager

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getHomeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        setPrefValue()
        attachObserver()
        setRecyclerView()
        setupToolbar(
            binding.toolbarHome.toolbar,
            "Home Fragment",
            false,
            titleTextColor = Color.BLACK,
            toolbarColor = getColor(requireActivity(), R.color.colorPrimary)
        )
        setDrawerLayout()
    }

    private fun setDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawerLayout,
            binding.toolbarHome.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item1 -> {
                    binding.drawerLayout.close()
                    requireActivity().showDialog(onPositiveBtnClick = {
                        homeViewModel.getHomeData()
                        getPrefValue()
                    }, message = "Are you sure you want to call api?", isShowNegativeButton = true)
                    true
                }
                R.id.menu_item2 -> {
                    binding.drawerLayout.close()
                    navController.navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun setRecyclerView() {
        homeAdapter = HomeAdapter(requireContext(), onItemClickListener = { result ->
            Logger.d("Result: $result")
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = homeAdapter
    }

    private fun attachObserver() {
        homeViewModel.musicAlbums.observe(viewLifecycleOwner) {
            when (it) {
                is BaseApiResponseModel.Loading -> {
                    showProgress()
                    Logger.d("Loading")
                }

                is BaseApiResponseModel.Success -> {
                    it.data?.let { result ->
                        homeAdapter.setData(result.feed?.results ?: emptyList())
                    }
                    hideProgress()
                    Logger.d(it.data.toString())
                }

                is BaseApiResponseModel.Error -> {
                    hideProgress()
                    Logger.d(it.message.toString())
                    requireActivity().showLongToast(it.message.toString())
                }
            }
        }
    }

    private fun setPrefValue() {
        val user = User(id = 1, name = "John Doe")
        lifecycleScope.launch {
            prefManager.setValue("test", "124")
        }
        lifecycleScope.launch {
            prefManager.setObject(stringPreferencesKey("user"), user)
        }
    }

    private fun getPrefValue() {
        lifecycleScope.launch {
            prefManager.getValue("test", "0").collectLatest {
                Logger.d("getValue: $it")
            }
        }
        lifecycleScope.launch {
            prefManager.getObject(stringPreferencesKey("user"), User(id = 0, name = ""))
                .collectLatest { retrievedUser ->
                    Logger.d("getUserObj: $retrievedUser")
                }
        }
    }
}