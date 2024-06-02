package com.example.androidboilerplate.repository.network

import com.example.androidboilerplate.data.network.apiService.ApiService
import com.example.androidboilerplate.data.network.baseModel.BaseApiResponseModel
import com.example.androidboilerplate.screens.home.model.network.MusicAlbums
import javax.inject.Inject

class BaseApiRepositoryImp @Inject constructor(private val apiService: ApiService) : BaseApiRepository {

    override suspend fun getMoviesAlbums(): BaseApiResponseModel<MusicAlbums> {
        return apiService.getMoviesAlbums()
    }
}