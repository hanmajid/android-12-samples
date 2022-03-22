package com.hanmajid.android.snowcone.dataaccessrationale

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.OnTokenCanceledListener

@RequiresApi(Build.VERSION_CODES.S)
class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationManager: LocationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    getLastLocation()
                } else {
                    Toast.makeText(this, "Please give location permission", Toast.LENGTH_LONG)
                        .show()
                }
            }

        checkPermissionOrGetLastLocation()
    }

    /**
     * Check whether the app has location permission or not. If it does, get last device location.
     * Otherwise, ask user to grant the permission.
     */
    private fun checkPermissionOrGetLastLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                requiredPermission,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLastLocation()
        } else {
            requestPermissionLauncher.launch(requiredPermission)
        }
    }

    /**
     * Get device last location if location service is enabled. If it is disabled, ask user to
     * enable it first.
     */
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (!locationManager.isLocationEnabled) {
            Toast.makeText(this, "Please enable location service first", Toast.LENGTH_LONG)
                .show()
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                        return this
                    }

                    override fun isCancellationRequested(): Boolean {
                        return true
                    }
                }
            ).addOnSuccessListener { location: Location? ->
                Log.wtf("TAG", location.toString())
                Toast.makeText(
                    this,
                    "Last location obtained! Now check your Privacy Dashboard or App Settings!",
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
    }

    companion object {
        const val requiredPermission = Manifest.permission.ACCESS_COARSE_LOCATION
    }
}