package com.hanmajid.android.snowcone.biometricpromptlocalizablestrings

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL

class MainActivity : AppCompatActivity() {
    private val biometricManager: BiometricManager by lazy {
        BiometricManager.from(this)
    }

    /**
     * You can change this value with any [BiometricManager.Authenticators] constant.
     */
    private val authenticators = BIOMETRIC_STRONG or DEVICE_CREDENTIAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        // Refresh strings.
        val strings = biometricManager.getStrings(authenticators)
        findViewById<TextView>(R.id.text_button_label).text = strings?.buttonLabel
        findViewById<TextView>(R.id.text_prompt_message).text = strings?.promptMessage
        findViewById<TextView>(R.id.text_setting_name).text = strings?.settingName
    }
}