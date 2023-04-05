package com.example.unifiedhrs

import android.R
import android.app.AlertDialog
import android.util.Log
import android.util.TypedValue
import android.view.Window
import android.view.WindowManager
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
        showInAppPermissionDialog()
    }

    private fun showInAppPermissionDialog() {
        Log.d(tag, "showInAppPermissionDialog() called")
        val dialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(this, R.style.Theme_Material_Light_Dialog_Alert)

        dialogBuilder.setTitle("Data Access Request")
        dialogBuilder.setMessage("Hospital ____ is requesting limited time access to your Personal History and Past Medical Records")
        dialogBuilder.setNegativeButton("Deny") {
                dialog, _ ->
            dialog.dismiss()
        }
        dialogBuilder.setPositiveButton("Allow") {
                dialog, _ ->

            uploadNewAccessDocumentToFirebase()
            dialog.dismiss()
        }

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

        dialogWindow.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
        dialogWindowAttributes.windowAnimations = R.style.Animation_Dialog
        dialog.show()
    }

    private fun uploadNewAccessDocumentToFirebase() {
        Log.d(tag, "RRG uploadNewAccessDocumentToFirebase() called")

        val dataAccessRecord = hashMapOf(
            IDENTIFIER_ACCESS_DATE to "Apr 07, 2022",
            IDENTIFIER_FLAG_EXPIRED to false,
            IDENTIFIER_FLAG_USER_PERMISSION to false,
            IDENTIFIER_ACCESS_HOSPITAL_NAME to "Alberta Hospital"
        )

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