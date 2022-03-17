package com.hanmajid.android.snowcone.actuatoreffects

import android.content.Context
import android.os.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.S)
class MainActivity : AppCompatActivity() {

    private val vibratorManager: VibratorManager by lazy {
        getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_low_tick).setOnClickListener {
            tryVibrate(VibrationEffect.Composition.PRIMITIVE_LOW_TICK)
        }
        findViewById<Button>(R.id.button_spin).setOnClickListener {
            tryVibrate(VibrationEffect.Composition.PRIMITIVE_SPIN)
        }
        findViewById<Button>(R.id.button_thud).setOnClickListener {
            tryVibrate(VibrationEffect.Composition.PRIMITIVE_THUD)
        }
    }

    /**
     * Check whether [effectId] is supported by the device's Vibrator.
     */
    private fun isPrimitiveSupported(effectId: Int): Boolean {
        return vibratorManager.defaultVibrator.areAllPrimitivesSupported(effectId)
    }

    /**
     * Try making vibration with the given [effectId] if the device supports it. Otherwise,
     * show error message.
     */
    private fun tryVibrate(effectId: Int) {
        if (isPrimitiveSupported(effectId)) {
            vibratorManager.vibrate(
                CombinedVibration.createParallel(
                    VibrationEffect.startComposition()
                        .addPrimitive(effectId)
                        .compose()
                )
            )
        } else {
            Toast.makeText(
                this,
                "This primitive is not supported by this device.",
                Toast.LENGTH_LONG,
            ).show()
        }
    }
}