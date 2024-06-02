package com.example.androidboilerplate.utils

import com.example.androidboilerplate.data.network.baseModel.BaseApiResponseModel
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLProtocol
import io.ktor.utils.io.errors.IOException

fun HttpRequestBuilder.setBaseUrl(baseUrl: String) {
    val (protocolString, host, portString, basePath) = Regex("(\\w+)://([\\w.-]+)(:(\\d+))?(.*)")
        .matchEntire(baseUrl)!!
        .groupValues
        .slice(listOf(1, 2, 4, 5))
    url.protocol = URLProtocol.createOrDefault(protocolString)
    url.host = host
    url.port = portString.toIntOrNull() ?: url.protocol.defaultPort
    url.encodedPath =
        if (url.encodedPath != "/") "$basePath/${url.encodedPath.trimEnd('/')}" else basePath
}
suspend fun <T> apiRequest(data: suspend () -> T): BaseApiResponseModel<T> {
    return try {
        BaseApiResponseModel.Success(data())
    } catch (e: RedirectResponseException) {
        BaseApiResponseModel.Error(errorMessage = e.response.status.description)
    } catch (e: ClientRequestException) {
        BaseApiResponseModel.Error(errorMessage = e.response.status.description)
    } catch (e: ServerResponseException) {
        BaseApiResponseModel.Error(errorMessage = e.response.status.description)
    } catch (e: IOException) {
        BaseApiResponseModel.Error(errorMessage = "Network Error!!")
    } catch (e: Exception) {
        BaseApiResponseModel.Error(errorMessage = e.message ?: "Unknown Error!!")
    }
}