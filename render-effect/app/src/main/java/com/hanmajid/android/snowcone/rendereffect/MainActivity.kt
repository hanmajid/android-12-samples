package com.hanmajid.android.snowcone.rendereffect

import android.graphics.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

@RequiresApi(Build.VERSION_CODES.S)
class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    private val blurRenderEffect = RenderEffect.createBlurEffect(10f, 10f, Shader.TileMode.CLAMP)
    private val colorFilterRenderEffect: RenderEffect by lazy {
        RenderEffect.createColorFilterEffect(
            BlendModeColorFilter(
                ContextCompat.getColor(this, R.color.purple_500),
                BlendMode.COLOR,
            )
        )
    }
    private val offsetRenderEffect = RenderEffect.createOffsetEffect(100f, 100f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.image)
        textView = findViewById(R.id.text)

        /**
         * Sets up single effect buttons.
         */
        findViewById<Button>(R.id.button_blur).setOnClickListener {
            imageView.setRenderEffect(blurRenderEffect)
            textView.setRenderEffect(blurRenderEffect)
        }
        findViewById<Button>(R.id.button_color_filter).setOnClickListener {
            imageView.setRenderEffect(colorFilterRenderEffect)
            textView.setRenderEffect(colorFilterRenderEffect)
        }
        findViewById<Button>(R.id.button_offset).setOnClickListener {
            imageView.setRenderEffect(offsetRenderEffect)
            textView.setRenderEffect(offsetRenderEffect)
        }
        findViewById<Button>(R.id.button_shader).setOnClickListener {
            imageView.setRenderEffect(getShaderRenderEffect(imageView))
            textView.setRenderEffect(getShaderRenderEffect(textView))
        }
        findViewById<Button>(R.id.button_bitmap).setOnClickListener {
            imageView.setRenderEffect(getBitmapRenderEffect(imageView))
            textView.setRenderEffect(getBitmapRenderEffect(textView))
        }

        /**
         * Sets up Chain Effect buttons.
         */
        findViewById<Button>(R.id.button_chain_effect_blur_color_filter).setOnClickListener {
            val chainEffect =
                RenderEffect.createChainEffect(blurRenderEffect, colorFilterRenderEffect)
            imageView.setRenderEffect(chainEffect)
            textView.setRenderEffect(chainEffect)
        }
        findViewById<Button>(R.id.button_chain_effect_color_filter_blur).setOnClickListener {
            val chainEffect =
                RenderEffect.createChainEffect(colorFilterRenderEffect, blurRenderEffect)
            imageView.setRenderEffect(chainEffect)
            textView.setRenderEffect(chainEffect)
        }

        /**
         * Sets up Blended Effect buttons.
         */
        findViewById<Button>(R.id.button_blend_mode_effect_blur_color_filter).setOnClickListener {
            val blendedEffect = RenderEffect.createBlendModeEffect(
                blurRenderEffect,
                colorFilterRenderEffect,
                BlendMode.COLOR,
            )
            imageView.setRenderEffect(blendedEffect)
            textView.setRenderEffect(blendedEffect)
        }
        findViewById<Button>(R.id.button_blend_mode_effect_color_filter_blur).setOnClickListener {
            val blendedEffect = RenderEffect.createBlendModeEffect(
                colorFilterRenderEffect,
                blurRenderEffect,
                BlendMode.COLOR,
            )
            imageView.setRenderEffect(blendedEffect)
            textView.setRenderEffect(blendedEffect)
        }

        // Sets up clear effect button.
        findViewById<Button>(R.id.button_clear).setOnClickListener {
            imageView.setRenderEffect(null)
            textView.setRenderEffect(null)
        }
    }

    /**
     * Get RenderEffect from [RenderEffect.createShaderEffect].
     *
     * The shader will create a linear gradient by the size of [view].
     */
    private fun getShaderRenderEffect(view: View): RenderEffect {
        return RenderEffect.createShaderEffect(
            LinearGradient(
                0f,
                0f,
                view.width.toFloat() / 2,
                view.height.toFloat() / 2,
                Color.BLUE,
                Color.RED,
                Shader.TileMode.CLAMP,
            )
        )
    }

    /**
     * Get RenderEffect from [RenderEffect.createBitmapEffect].
     *
     * The bitmap will be the size of [view].
     */
    private fun getBitmapRenderEffect(view: View): RenderEffect {
        val pokemon = BitmapFactory.decodeResource(resources, R.drawable.pokemon_2)
        return RenderEffect.createBitmapEffect(
            Bitmap.createScaledBitmap(
                pokemon,
                view.width,
                view.height,
                false,
            )
        )
    }
}