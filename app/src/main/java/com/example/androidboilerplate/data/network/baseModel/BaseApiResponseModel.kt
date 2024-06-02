package com.example.androidboilerplate.data.network.baseModel

sealed class BaseApiResponseModel<out T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : BaseApiResponseModel<T>(data = data)
    class Error<T>(errorMessage: String) : BaseApiResponseModel<T>(message = errorMessage)
    class Loading<T> : BaseApiResponseModel<T>()
}

