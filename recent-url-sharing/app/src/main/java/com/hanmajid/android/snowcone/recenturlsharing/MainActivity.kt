package com.hanmajid.android.snowcone.recenturlsharing

import android.app.assist.AssistContent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    var recentUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Everytime destination changes, update the recent URL.
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController.addOnDestinationChangedListener { _, navDestination, _ ->
            if (navDestination.id == R.id.firstFragment) {
                recentUrl = "https://example.com/first"
            } else if (navDestination.id == R.id.secondFragment) {
                recentUrl = "https://example.com/second"
            }
        }
    }

    override fun onProvideAssistContent(outContent: AssistContent?) {
        super.onProvideAssistContent(outContent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && recentUrl != null) {
            outContent?.webUri = Uri.parse(recentUrl)
        }
    }
}