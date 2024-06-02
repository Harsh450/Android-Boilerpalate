package com.example.androidboilerplate.data.network.apiService

import com.example.androidboilerplate.data.network.baseModel.BaseApiResponseModel
import com.example.androidboilerplate.screens.home.model.network.MusicAlbums
import com.example.androidboilerplate.utils.ApiEndPoints
import com.example.androidboilerplate.utils.apiRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url


class ApiService(private val client: HttpClient) {

    suspend fun getMoviesAlbums(): BaseApiResponseModel<MusicAlbums> {
        return apiRequest {
            client.get {
                url(path = ApiEndPoints.GET_MOVIES)
            }
        }
    }
}