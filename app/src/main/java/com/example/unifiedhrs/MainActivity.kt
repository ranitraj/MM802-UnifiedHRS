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
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private val tag: String? = MainActivity::class.simpleName

    private val mDatabaseInstance = Firebase.firestore
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = mBinding.root

        setContentView(view)
        askNotificationPermission()

        initUI()
        initClickListeners()
        initFirebase()
    }

    private fun initFirebase() {
        // Cloud Messaging Notification Service Initialization
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e(tag, "RRG Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.e(tag, "RRG Token Obtained = $token")
        })

        // Fire Initial API
        fetchPatientInformationFromFirebase()
    }

    private fun initUI() {
        mBinding.progressCircular.visibility = View.VISIBLE
        mBinding.layoutParent.visibility = View.GONE
    }

    private fun initClickListeners() {
        mBinding.tvPatientInfo.setOnClickListener {
            initUI()
            fetchPatientInformationFromFirebase()
        }

        mBinding.tvMedicalHistory.setOnClickListener {
            initUI()
            fetchMedicalHistoryFromFirebase()
        }

        mBinding.tvAccess.setOnClickListener {
            initUI()
            showAccessUI()
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
        mBinding.layoutParent.visibility = View.VISIBLE

        mBinding.layoutPatientInfo.visibility = View.VISIBLE
        mBinding.layoutMedicalHistory.visibility = View.GONE
        mBinding.layoutAccess.visibility = View.GONE

        mBinding.tvPatientInfo.setTextColor(resources.getColor(R.color.lightgrey))
        mBinding.tvMedicalHistory.setTextColor(resources.getColor(R.color.unselected))
        mBinding.tvAccess.setTextColor(resources.getColor(R.color.unselected))
    }

    private fun showMedicalHistoryUI() {
        mBinding.layoutParent.visibility = View.VISIBLE

        mBinding.layoutMedicalHistory.visibility = View.VISIBLE
        mBinding.layoutPatientInfo.visibility = View.GONE
        mBinding.layoutAccess.visibility = View.GONE

        mBinding.tvPatientInfo.setTextColor(resources.getColor(R.color.unselected))
        mBinding.tvMedicalHistory.setTextColor(resources.getColor(R.color.lightgrey))
        mBinding.tvAccess.setTextColor(resources.getColor(R.color.unselected))
    }

    private fun showAccessUI() {
        mBinding.layoutParent.visibility = View.VISIBLE

        mBinding.layoutAccess.visibility = View.VISIBLE
        mBinding.layoutMedicalHistory.visibility = View.GONE
        mBinding.layoutPatientInfo.visibility = View.GONE

        mBinding.tvMedicalHistory.setTextColor(resources.getColor(R.color.unselected))
        mBinding.tvPatientInfo.setTextColor(resources.getColor(R.color.unselected))
        mBinding.tvAccess.setTextColor(resources.getColor(R.color.lightgrey))
    }

    private fun fetchPatientInformationFromFirebase() {
        Log.d(tag, "RRG fetchPatientInformationFromFirebase() called")
        mDatabaseInstance.collection(USER_COLLECTION_IDENTIFIER)
            .document(LOGGED_IN_PATIENT_ID)
            .get()
            .addOnSuccessListener { data ->
                mBinding.progressCircular.visibility = View.GONE

                showPatientHistoryUI()
                parsePatientInformationData(data)
            }
            .addOnFailureListener {
                mBinding.progressCircular.visibility = View.GONE
                Log.e(tag, "RRG addOnFailureListener() called for fetchPatientInformationFromFirebase() with exception = ${it.localizedMessage}")

                Toast.makeText(
                    this,
                    "Something went wrong!",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun fetchMedicalHistoryFromFirebase() {
        Log.d(tag, "RRG fetchMedicalHistoryFromFirebase() called")
        mDatabaseInstance.collection(USER_COLLECTION_MEDICAL_HISTORY)
            .document(LOGGED_IN_PATIENT_ID)
            .collection(USER_COLLECTION_RECORD)
            .get()
            .addOnSuccessListener { dataList ->
                mBinding.progressCircular.visibility = View.GONE

                for (document in dataList) {
                    Log.d(tag, "RRG ${document.id} => ${document.data}")
                }

                showMedicalHistoryUI()
                parseMedicalHistoryData(dataList)
            }
            .addOnFailureListener {
                mBinding.progressCircular.visibility = View.GONE
                Log.e(tag, "RRG addOnFailureListener() called for fetchMedicalHistoryFromFirebase() with exception = ${it.localizedMessage}")

                Toast.makeText(
                    this,
                    "Something went wrong!",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun parsePatientInformationData(data: DocumentSnapshot) {
        mBinding.tvPatientName.text = data.get(IDENTIFIER_USER_NAME).toString()
        mBinding.tvInsuranceClaimPercentage.text = data.get(IDENTIFIER_INSURANCE_CLAIM_PERCENT).toString()
        mBinding.tvHospitalVisitCount.text = data.get(IDENTIFIER_HOSPITAL_VISIT_COUNT).toString()

        mBinding.tvPatientComments.text = data.get(IDENTIFIER_PATIENT_COMMENTS).toString()
        mBinding.tvPatientHeight.text = data.get(IDENTIFIER_PATIENT_HEIGHT).toString()
        mBinding.tvPatientWeight.text = data.get(IDENTIFIER_PATIENT_WEIGHT).toString()
        mBinding.tvPatientBloodPressure.text = data.get(IDENTIFIER_PATIENT_BLOOD_PRESSURE).toString()

        mBinding.tvPatientMobileNumber.text = data.get(IDENTIFIER_PATIENT_CONTACT_NUMBER).toString()
        mBinding.tvPatientEmail.text = data.get(IDENTIFIER_PATIENT_EMAIL).toString()
        mBinding.tvPatientLocation.text = data.get(IDENTIFIER_PATIENT_LOCATION).toString()
    }

    private fun parseMedicalHistoryData(dataList: QuerySnapshot) {

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
}