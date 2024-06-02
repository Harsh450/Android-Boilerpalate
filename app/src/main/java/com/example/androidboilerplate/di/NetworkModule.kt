package com.example.androidboilerplate.di

import com.example.androidboilerplate.BuildConfig
import com.example.androidboilerplate.data.network.apiService.ApiService
import com.example.androidboilerplate.repository.network.BaseApiRepository
import com.example.androidboilerplate.repository.network.BaseApiRepositoryImp
import com.example.androidboilerplate.utils.setBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import javax.inject.Singleton
import com.example.androidboilerplate.utils.Logger as DefaultLogger

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun httpClientProvider(): HttpClient {
        return HttpClient(Android) {
            defaultRequest {
                setBaseUrl(BuildConfig.API_BASE_URL)
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        if (BuildConfig.DEBUG) DefaultLogger.d(message) else DefaultLogger.i(message)
                    }
                }
                level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.INFO
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    encodeDefaults = false
                    ignoreUnknownKeys = true
                    isLenient = true
                    allowSpecialFloatingPointValues = true
                    prettyPrint = false
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 60_000
                connectTimeoutMillis = 60_000
                socketTimeoutMillis = 60_000
            }
        }
    }

    @Provides
    @Singleton
    fun providesService(client: HttpClient) = ApiService(client)

    @Provides
    @Singleton
    fun providesApiRepository(api: ApiService): BaseApiRepository {
        return BaseApiRepositoryImp(api)
    }
}

