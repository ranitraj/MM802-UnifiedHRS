package com.example.unifiedhrs

import android.R
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.Window
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseNotificationService: FirebaseMessagingService() {
    private val tag: String? = FirebaseNotificationService::class.simpleName

    private val mDatabaseInstance = Firebase.firestore

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(tag, "RRG onNewToken() called with: token = $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e(tag, "RRG onMessageReceived() called with: message = $message")
        showNotification()
        uploadNewAccessDocumentToFirebase(true)
    }

    private fun showNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        // Assign channel ID
        val channelId = "notification_channel"

        // Pass the intent to PendingIntent to start the
        // next Activity
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        val builder = NotificationCompat
            .Builder(applicationContext, channelId)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContentTitle("Data Access Request")
            .setContentText("Hospital is requesting limited time access to your Personal History and Past Medical Records\"")
            .setSmallIcon(R.drawable.stat_notify_more)

        // Create an object of NotificationManager class to
        // notify the
        // user of events that happen in the background.
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId, "web_app",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
            Log.d(tag, "showNotification: version > O")
        }
        notificationManager.notify(0, builder.build())
    }

    private fun showInAppPermissionDialog() {
        Log.d(tag, "showInAppPermissionDialog() called")

        val dialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(applicationContext, R.style.Theme_Material_Light_Dialog_Alert)

        dialogBuilder.setTitle("Data Access Request")
        dialogBuilder.setMessage("Hospital ____ is requesting limited time access to your Personal History and Past Medical Records")
        dialogBuilder.setNegativeButton("Deny") {
                dialog, _ ->

            uploadNewAccessDocumentToFirebase(false)
            dialog.dismiss()
        }
        dialogBuilder.setPositiveButton("Allow") {
                dialog, _ ->

            uploadNewAccessDocumentToFirebase(true)
            dialog.dismiss()
        }


        Handler(Looper.getMainLooper()).postDelayed({

            val dialog: AlertDialog = dialogBuilder.create()
            val dialogWindow: Window? = dialog.window
            val dialogWindowAttributes: WindowManager.LayoutParams = dialogWindow!!.attributes

            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialogWindowAttributes)
            layoutParams.width =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350f, resources.displayMetrics)
                    .toInt()
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            dialogWindow.attributes = layoutParams

            dialogWindow.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL)
            dialogWindowAttributes.windowAnimations = R.style.Animation_Dialog
            dialog.show()

        }, 1000L)


    }

    private fun uploadNewAccessDocumentToFirebase(isAccessGiven: Boolean) {
        Log.d(tag, "uploadNewAccessDocumentToFirebase() called with: isAccessGiven = $isAccessGiven")

        // Data Preparation
        val dataAccessRecord = if (isAccessGiven) {
            hashMapOf(
                IDENTIFIER_ACCESS_DATE to "Apr 07, 2022",
                IDENTIFIER_FLAG_EXPIRED to false,
                IDENTIFIER_FLAG_USER_PERMISSION to true,
                IDENTIFIER_ACCESS_HOSPITAL_NAME to "Alberta Hospital"
            )
        } else {
            hashMapOf(
                IDENTIFIER_ACCESS_DATE to "Apr 07, 2022",
                IDENTIFIER_FLAG_EXPIRED to false,
                IDENTIFIER_FLAG_USER_PERMISSION to false,
                IDENTIFIER_ACCESS_HOSPITAL_NAME to "Alberta Hospital"
            )
        }

        // Upload
        mDatabaseInstance.collection(USER_COLLECTION_DATA_ACCESS)
            .document(LOGGED_IN_PATIENT_ID)
            .collection(USER_COLLECTION_RECORD)
            .add(dataAccessRecord)
            .addOnSuccessListener { documentReference ->
                Log.d(tag, "RRG DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e(tag, "RRG Error adding document", e)
            }
    }

}