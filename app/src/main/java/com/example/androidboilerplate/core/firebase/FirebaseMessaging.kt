package com.example.androidboilerplate.core.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.androidboilerplate.R
import com.example.androidboilerplate.screens.HomeActivity
import com.example.androidboilerplate.utils.AppConstants
import com.example.androidboilerplate.utils.Logger
import com.example.androidboilerplate.utils.PrefManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

open class FirebaseMessaging : FirebaseMessagingService() {

    private val TAG = "FirebaseMessaging"
    private var mNotificationId: Int = 0
    private var mContext: Context? = null
    @Inject
    lateinit var prefManager: PrefManager
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    companion object {

        const val FCM_KEY_TITLE = "title"
        const val FCM_KEY_MESSAGE = "message"
        const val FCM_KEY_ID = "id"
        const val FCM_KEY_TYPE = "type"
        const val FCM_KEY_DATA = "data"
        const val FCM_KEY_ADDITIONAL_DATA = "additional_data"

        const val TYPE_PAYMENT = "Payment"
        const val TYPE_ORDER = "Order"
        const val TYPE_REGISTER = "Register"
        const val TYPE_WALLET_POINT = "Walletpoint"
        const val TYPE_ORDER_STATUS = "Orderstatus"
        const val TYPE_PROMO_CODE = "Promocode"

        const val NOTIFICATION_ID_NEW_MESSAGE = 10005

    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        coroutineScope.launch {
            prefManager.setValue(AppConstants.PUSH_TOKEN_KEY, token)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Logger.d("From : " + remoteMessage.from)
        Logger.d("getData() : " + remoteMessage.data)
        Logger.d("getNotification() : " + remoteMessage.notification)

        mContext = this
        remoteMessage.apply {
            createNotification(remoteMessage)
        }
    }

    private fun createNotification(remoteMessage: RemoteMessage) {
        val messageData = remoteMessage.data
        Logger.e(messageData.toString())

        var id: String? = ""
        var type: String? = ""
        var title: String? = ""
        var message: String? = ""
        var additionalData: String? = ""
        var notificationData: NotificationData? = null

        var pendingIntent: PendingIntent? = null

        id = messageData[FCM_KEY_ID]
        type = messageData[FCM_KEY_TYPE]
        title = messageData[FCM_KEY_TITLE]
        message = messageData[FCM_KEY_MESSAGE]
        additionalData = messageData[FCM_KEY_ADDITIONAL_DATA]

        if (additionalData?.isNotEmpty() == true) {
            notificationData = Gson().fromJson(additionalData, NotificationData::class.java)
        }

        mNotificationId = System.currentTimeMillis().toInt()

        pendingIntent = getIntentForHome(type.orEmpty(), notificationData)

        handleNotification(title.orEmpty(), message.orEmpty(), pendingIntent, mNotificationId)
    }

    private fun handleNotification(
        title: String,
        content: String,
        pendingIntent: PendingIntent?,
        notificationId: Int
    ) {

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, getString(R.string.app_name))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.mipmap.ic_launcher
                )
            )
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.app_name),
                getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(
            notificationId, notificationBuilder.build()
        )
    }

    private fun getIntentForHome(
        type: String,
        notificationData: NotificationData?
    ): PendingIntent? {
        val resultIntent = Intent(this, HomeActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val bundle = Bundle()

        bundle.putString(AppConstants.EXTRA_TYPE, type)
        if (notificationData != null) {
            bundle.putSerializable(NotificationData::class.java.simpleName, notificationData)
        }

        resultIntent.putExtras(bundle)

        return PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    @Serializable
    data class NotificationData(
        @SerializedName(AppConstants.EXTRA_ID)
        var orderId: String? = ""
    ) : java.io.Serializable
}