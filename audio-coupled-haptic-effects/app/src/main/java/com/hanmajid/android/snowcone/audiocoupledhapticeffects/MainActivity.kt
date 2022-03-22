package com.hanmajid.android.snowcone.audiocoupledhapticeffects

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.media.audiofx.HapticGenerator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.FileDescriptor
import java.io.IOException

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_play_audio).setOnClickListener {
            val audioFileResource = R.raw.sample_audio
            val afd: AssetFileDescriptor = resources.openRawResourceFd(audioFileResource)
            val fileDescriptor: FileDescriptor = afd.fileDescriptor
            val player = MediaPlayer()
            try {
                player.setDataSource(
                    fileDescriptor, afd.startOffset,
                    afd.length
                )
                Log.wtf("audioSessionId", player.audioSessionId.toString())
                val hapticGenerator = if (HapticGenerator.isAvailable()) {
                    HapticGenerator.create(player.audioSessionId)
                } else {
                    Toast.makeText(
                        this,
                        "HapticGenerator is not available on this device :(",
                        Toast.LENGTH_LONG,
                    ).show()
                    null
                }
                val status = hapticGenerator?.setEnabled(true)
                Log.wtf("status", status.toString())
                Log.wtf("enabled", hapticGenerator?.enabled.toString())
                Thread.sleep(1000)

                player.isLooping = false
                player.prepare()
                player.start()

//                hapticGenerator?.release()
//                hapticGenerator?.close()
            } catch (ex: IOException) {
                Log.wtf(ex.localizedMessage, ex)
            }
        }
    }
}