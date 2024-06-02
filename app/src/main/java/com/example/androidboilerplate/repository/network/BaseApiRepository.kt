package com.example.androidboilerplate.repository.network

import com.example.androidboilerplate.data.network.baseModel.BaseApiResponseModel
import com.example.androidboilerplate.screens.home.model.network.MusicAlbums


interface BaseApiRepository {

    suspend fun getMoviesAlbums(): BaseApiResponseModel<MusicAlbums>
}