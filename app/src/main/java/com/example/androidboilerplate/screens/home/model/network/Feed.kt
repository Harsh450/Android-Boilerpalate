package com.example.androidboilerplate.screens.home.model.network


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Feed(
    @SerialName("author")
    val author: Author,
    @SerialName("copyright")
    val copyright: String,
    @SerialName("country")
    val country: String,
    @SerialName("icon")
    val icon: String,
    @SerialName("id")
    val id: String,
    @SerialName("links")
    val links: List<Link>,
    @SerialName("results")
    val results: List<Result>,
    @SerialName("title")
    val title: String,
    @SerialName("updated")
    val updated: String
)