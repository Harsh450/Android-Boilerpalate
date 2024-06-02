package com.example.androidboilerplate.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.androidboilerplate.core.BaseFragment
import com.example.androidboilerplate.databinding.FragmentLoginBinding
import com.example.androidboilerplate.utils.PrefManager
import com.example.androidboilerplate.utils.isEmailValid
import com.example.androidboilerplate.utils.isPasswordValid
import com.example.androidboilerplate.utils.setOnSafeClickListener
import com.example.androidboilerplate.utils.showLongToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
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
        setClickListener()
    }

    private fun setClickListener() {
        binding.buttonLogin.setOnSafeClickListener {
            val email = binding.editTextEmail.text
            val pass = binding.editTextPassword.text
            if (isEmailValid(email.toString()) && isPasswordValid(pass.toString())) {
                setPrefValue()
                navController.navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            } else if (!isEmailValid(email.toString())) {
                requireActivity().showLongToast("Please enter valid email.")
            } else {
                requireActivity().showLongToast("Please enter valid password.")
            }
        }
        binding.textViewSignUp.setOnSafeClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
    }

    private fun setPrefValue() {
        lifecycleScope.launch {
            prefManager.setValue("isLogin", true)
        }
    }
}