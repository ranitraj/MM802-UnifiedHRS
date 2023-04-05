package com.example.unifiedhrs

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.unifiedhrs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val tag: String? = MainActivity::class.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root

        setContentView(view)

        initUI()
        initClickListeners()
    }

    private fun initUI() {
        binding.layoutPatientInfo.visibility = View.VISIBLE
        binding.layoutMedicalHistory.visibility = View.GONE
        binding.layoutAccess.visibility = View.GONE
    }

    private fun initClickListeners() {
        binding.tvPatientInfo.setOnClickListener {
            showPatientHistoryUI()

            binding.tvPatientInfo.setTextColor(resources.getColor(R.color.lightgrey))
            binding.tvMedicalHistory.setTextColor(resources.getColor(R.color.unselected))
            binding.tvAccess.setTextColor(resources.getColor(R.color.unselected))
        }

        binding.tvMedicalHistory.setOnClickListener {
            showMedicalHistoryUI()

            binding.tvPatientInfo.setTextColor(resources.getColor(R.color.unselected))
            binding.tvMedicalHistory.setTextColor(resources.getColor(R.color.lightgrey))
            binding.tvAccess.setTextColor(resources.getColor(R.color.unselected))
        }

        binding.tvAccess.setOnClickListener {
            showAccessUI()

            binding.tvMedicalHistory.setTextColor(resources.getColor(R.color.unselected))
            binding.tvPatientInfo.setTextColor(resources.getColor(R.color.unselected))
            binding.tvAccess.setTextColor(resources.getColor(R.color.lightgrey))
        }
    }

    private fun showPatientHistoryUI() {
        binding.layoutPatientInfo.visibility = View.VISIBLE
        binding.layoutMedicalHistory.visibility = View.GONE
        binding.layoutAccess.visibility = View.GONE
    }

    private fun showMedicalHistoryUI() {
        binding.layoutMedicalHistory.visibility = View.VISIBLE
        binding.layoutPatientInfo.visibility = View.GONE
        binding.layoutAccess.visibility = View.GONE
    }

    private fun showAccessUI() {
        binding.layoutAccess.visibility = View.VISIBLE
        binding.layoutMedicalHistory.visibility = View.GONE
        binding.layoutPatientInfo.visibility = View.GONE
    }
}