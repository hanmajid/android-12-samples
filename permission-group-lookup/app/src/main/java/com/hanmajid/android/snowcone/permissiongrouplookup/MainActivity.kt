package com.hanmajid.android.snowcone.permissiongrouplookup

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val executor = Executors.newSingleThreadExecutor()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Gets the permission group of `ACCESS_FINE_LOCATION` permission.
        packageManager.getGroupOfPlatformPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            executor,
        ) {
            runOnUiThread {
                findViewById<TextView>(R.id.permission_group).text = it
            }
        }
        // Gets the permissions of `STORAGE` permission group.
        packageManager.getPlatformPermissionsForGroup(
            Manifest.permission_group.STORAGE,
            executor,
        ) {
            runOnUiThread {
                findViewById<TextView>(R.id.permissions).text = it.joinToString(", ")
            }
        }
    }
}