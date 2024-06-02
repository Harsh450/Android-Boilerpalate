package com.example.androidboilerplate.screens.home.model.network


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("genreId")
    val genreId: String,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)