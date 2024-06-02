package com.example.androidboilerplate.core.location

import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.androidboilerplate.utils.AppConstants
import com.example.androidboilerplate.utils.Logger
import com.example.androidboilerplate.utils.PrefManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject


open class MyLocationManager(private val context: Context) {

    var myLocationManager: MyLocationListener? = null
    private var job = Job()
    private var locationRequest: LocationRequest? = null
    private var liveLocationList: MutableList<String> = arrayListOf()

    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private lateinit var locationCallback: LocationCallback
    @Inject
    lateinit var prefManager: PrefManager
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    init {
        locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(10000)
    }

    fun addLiveLocationUser(id: String) {
        if (id.isNotEmpty() && !liveLocationList.contains(id)) {
            liveLocationList.add(id)
        }
    }

    fun removeLiveLocationUser(id: String) {
        if (id.isNotEmpty() && liveLocationList.contains(id)) {
            liveLocationList.remove(id)
        }
    }

    fun startLocationLive() {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            Logger.d("Live location sharing")

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations) {
                        Logger.d("Live location sharing $location")
                        myLocationManager?.onLocationReceived(location)
                    }
                }
            }
            locationRequest?.let {
                fusedLocationClient.requestLocationUpdates(
                    it,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        }
    }

    fun startLocation() {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Logger.d("Location sharing")
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations) {
                        Logger.d("Live location sharing $location")
                        coroutineScope.launch {
                            prefManager.setValue(AppConstants.LATITUDE, location?.latitude?.toString())
                        }
                        coroutineScope.launch {
                            prefManager.setValue(AppConstants.LONGITUDE, location?.longitude?.toString())
                        }
                        myLocationManager?.onLocationReceived(location)
                        stopLocation()
                    }
                }
            }
            locationRequest?.let {
                fusedLocationClient.requestLocationUpdates(
                    it,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        } else {
            Logger.d("Location permission denied")
        }
    }

    fun stopLocation() {
        if (liveLocationList.isEmpty()) {
            onClear()
        }
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun onClear() {
        Logger.d("Location sharing off")
        job.cancel()
    }

}