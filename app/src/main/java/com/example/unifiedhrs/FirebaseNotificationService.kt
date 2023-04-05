package com.example.unifiedhrs

import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseNotificationService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

}