package com.example.androidboilerplate.screens.profile

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat.getColor
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.androidboilerplate.R
import com.example.androidboilerplate.core.BaseFragment
import com.example.androidboilerplate.databinding.FragmentProfileBinding
import com.example.androidboilerplate.utils.setOnSafeClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                    navController.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        setOnclickListener()
        setupToolbar(
            binding.toolbarCreateProfile.toolbar,
            "Profile Fragment",
            true,
            titleTextColor = Color.BLACK,
            toolbarColor = getColor(requireActivity(), R.color.colorPrimary),
            onBackClickListener = { navController.popBackStack() }
        )
    }

    private fun setOnclickListener() {
        binding.goToWebView.setOnSafeClickListener {
            navController.navigate(ProfileFragmentDirections.actionProfileFragmentToWebViewFragment())
        }
    }
}