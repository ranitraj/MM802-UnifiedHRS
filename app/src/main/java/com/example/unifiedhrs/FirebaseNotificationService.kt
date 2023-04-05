package com.example.unifiedhrs

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService: FirebaseMessagingService() {
    private val tag: String? = FirebaseNotificationService::class.simpleName

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(tag, "RRG onNewToken() called with: token = $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e(tag, "RRG onMessageReceived() called with: message = $message")
    }

}