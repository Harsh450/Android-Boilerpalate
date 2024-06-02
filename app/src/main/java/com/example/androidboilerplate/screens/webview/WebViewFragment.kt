package com.example.androidboilerplate.screens.webview

import android.annotation.SuppressLint
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
import com.example.androidboilerplate.core.BaseActivity
import com.example.androidboilerplate.core.BaseFragment
import com.example.androidboilerplate.databinding.FragmentWebviewBinding
import com.example.androidboilerplate.utils.CustomWebViewClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment() {

    private lateinit var binding : FragmentWebviewBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.webView.canGoBack()){
                    binding.webView.goBack()
                } else {
                    navController.popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        setupToolbar(
            binding.toolbarProfile.toolbar,
           "WebView Fragment",
            true,
            titleTextColor = Color.BLACK,
            toolbarColor = getColor(requireActivity(), R.color.colorPrimary),
            onBackClickListener = { navController.popBackStack() }
        )
        setupWebViewClient()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebViewClient() {
        val webViewClient = CustomWebViewClient(requireActivity() as BaseActivity)
        binding.webView.webViewClient = webViewClient
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
        binding.webView.loadUrl("https://www.geeksforgeeks.org/")
    }
}