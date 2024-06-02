package com.example.androidboilerplate.screens.home.model.network


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusicAlbums(
    @SerialName("feed")
    val feed: Feed?=null
)