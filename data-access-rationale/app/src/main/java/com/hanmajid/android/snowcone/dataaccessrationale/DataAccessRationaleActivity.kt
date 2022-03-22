package com.hanmajid.android.snowcone.dataaccessrationale

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class DataAccessRationaleActivity : AppCompatActivity() {
    private lateinit var originTextView: TextView
    private lateinit var timestampTextView: TextView
    private lateinit var permissionGroupTextView: TextView

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_access_rationale)

        originTextView = findViewById(R.id.text_origin)
        timestampTextView = findViewById(R.id.text_timestamp)
        permissionGroupTextView = findViewById(R.id.text_permission_group)

        when (intent.action) {
            Intent.ACTION_VIEW_PERMISSION_USAGE -> {
                // User comes from app permission's page in system settings.
                originTextView.text = "App's permission page"
                permissionGroupTextView.text =
                    intent.getStringExtra(Intent.EXTRA_PERMISSION_GROUP_NAME)
            }
            Intent.ACTION_VIEW_PERMISSION_USAGE_FOR_PERIOD -> {
                // User comes from Privacy Dashboard screen.
                originTextView.text = "Privacy Dashboard"
                permissionGroupTextView.text =
                    intent.getStringExtra(Intent.EXTRA_PERMISSION_GROUP_NAME)
                val startTime = Date(intent.getLongExtra(Intent.EXTRA_START_TIME, -1L))
                val endTime = Date(intent.getLongExtra(Intent.EXTRA_END_TIME, -1L))
                val timestamp = "$startTime - $endTime"
                timestampTextView.text = timestamp
            }
            else -> {
                // User comes from other places.
            }
        }
    }
}