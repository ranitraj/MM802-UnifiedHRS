package com.example.unifiedhrs

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.unifiedhrs.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private val tag: String? = MainActivity::class.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root

        setContentView(view)
        askNotificationPermission()

        initUI()
        initClickListeners()
        initFirebase()
    }

    private fun initFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) { Log.w(tag, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.e(tag, "RRG Token = $token")
            Toast.makeText(baseContext, "Token obtained", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initUI() {
        binding.layoutPatientInfo.visibility = View.VISIBLE
        binding.layoutMedicalHistory.visibility = View.GONE
        binding.layoutAccess.visibility = View.GONE
    }

    private fun initClickListeners() {
        binding.tvPatientInfo.setOnClickListener {
            showPatientHistoryUI()
        }

        binding.tvMedicalHistory.setOnClickListener {
            showMedicalHistoryUI()
        }

        binding.tvAccess.setOnClickListener {
            showAccessUI()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(
                this,
                "Notification Permission Granted!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                this,
                "Unified HRS will NOT show Notifications!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // Ask for permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showPatientHistoryUI() {
        binding.layoutPatientInfo.visibility = View.VISIBLE
        binding.layoutMedicalHistory.visibility = View.GONE
        binding.layoutAccess.visibility = View.GONE

        binding.tvPatientInfo.setTextColor(resources.getColor(R.color.lightgrey))
        binding.tvMedicalHistory.setTextColor(resources.getColor(R.color.unselected))
        binding.tvAccess.setTextColor(resources.getColor(R.color.unselected))
    }

    private fun showMedicalHistoryUI() {
        binding.layoutMedicalHistory.visibility = View.VISIBLE
        binding.layoutPatientInfo.visibility = View.GONE
        binding.layoutAccess.visibility = View.GONE

        binding.tvPatientInfo.setTextColor(resources.getColor(R.color.unselected))
        binding.tvMedicalHistory.setTextColor(resources.getColor(R.color.lightgrey))
        binding.tvAccess.setTextColor(resources.getColor(R.color.unselected))
    }

    private fun showAccessUI() {
        binding.layoutAccess.visibility = View.VISIBLE
        binding.layoutMedicalHistory.visibility = View.GONE
        binding.layoutPatientInfo.visibility = View.GONE

        binding.tvMedicalHistory.setTextColor(resources.getColor(R.color.unselected))
        binding.tvPatientInfo.setTextColor(resources.getColor(R.color.unselected))
        binding.tvAccess.setTextColor(resources.getColor(R.color.lightgrey))
    }
}