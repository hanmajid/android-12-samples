package com.hanmajid.android.snowcone.richcontentinsertion

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.core.view.ContentInfoCompat
import androidx.core.view.ViewCompat
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    lateinit var textInputEditText: TextInputEditText

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this

        // Specify input view.
        textInputEditText = findViewById(R.id.text_input)

        // Specify accepted MIME types.
        val mimeTypes = arrayOf("image/*")

        // Set the rich content insertion listener.
        ViewCompat.setOnReceiveContentListener(
            textInputEditText,
            mimeTypes
        ) { _: View, contentInfo: ContentInfoCompat ->
            val (uriContent, remaining) = contentInfo.partition { item ->
                item.uri != null
            }
            if (uriContent != null) {
                // App-specific logic to handle the image insertion.
                val item = contentInfo.clip.getItemAt(0)
                val imageUri = uriContent.linkUri ?: item.uri
                val imageView = findViewById<AppCompatImageView>(R.id.image_view)
                Picasso.get()
                    .load(imageUri)
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            // Do nothing
                        }

                        override fun onError(e: Exception?) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG)
                                .show()
                        }
                    })
            }
            // Return anything that your app didn't handle. This preserves the default platform
            // behavior for text and anything else that you aren't implementing custom handling for.
            remaining
        }
    }
}