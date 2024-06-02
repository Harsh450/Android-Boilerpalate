package com.example.androidboilerplate.screens.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.androidboilerplate.core.BaseFragment
import com.example.androidboilerplate.databinding.FragmentSignUpBinding
import com.example.androidboilerplate.utils.setOnSafeClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setClickListener()
    }

    private fun setClickListener() {
        binding.buttonSignUp.setOnSafeClickListener {
            navController.navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }
        binding.textViewLogin.setOnSafeClickListener {
            navController.navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }
    }
}