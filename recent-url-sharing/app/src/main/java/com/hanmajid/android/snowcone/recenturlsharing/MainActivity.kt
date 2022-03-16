package com.hanmajid.android.snowcone.recenturlsharing

import android.app.assist.AssistContent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onProvideAssistContent(outContent: AssistContent?) {
        super.onProvideAssistContent(outContent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            outContent?.webUri = Uri.parse("https://google.com")
        }
    }
}