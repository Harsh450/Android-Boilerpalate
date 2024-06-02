package com.example.androidboilerplate.screens.home.model.local

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, val name: String)