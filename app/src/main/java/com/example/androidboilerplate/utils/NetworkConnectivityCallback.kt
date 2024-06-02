package com.example.androidboilerplate.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData

class NetworkConnectivityCallback(val context: Context) : LiveData<Boolean>() {

    private var connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        connectivityManager.registerDefaultNetworkCallback(getConnectivityManagerCallback())
        postValue(isConnected())
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }

    private fun getConnectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                postValue(true)
            }
            override fun onLost(network: Network) {
                postValue(false)
            }
            override fun onUnavailable() {
                postValue(false)
            }
        }
        return connectivityManagerCallback
    }

    private fun isConnected(): Boolean {
        var result = false
        connectivityManager.run {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
        return result
    }
}