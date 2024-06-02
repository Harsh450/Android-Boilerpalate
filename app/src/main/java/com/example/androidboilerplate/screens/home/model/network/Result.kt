package com.example.androidboilerplate.screens.home.model.network


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("artistId")
    val artistId: String?=null,
    @SerialName("artistName")
    val artistName: String,
    @SerialName("artistUrl")
    val artistUrl: String?=null,
    @SerialName("artworkUrl100")
    val artworkUrl100: String,
    @SerialName("contentAdvisoryRating")
    val contentAdvisoryRating: String?=null,
    @SerialName("genres")
    val genres: List<Genre>,
    @SerialName("id")
    val id: String,
    @SerialName("kind")
    val kind: String,
    @SerialName("name")
    val name: String,
    @SerialName("releaseDate")
    val releaseDate: String,
    @SerialName("url")
    val url: String
)