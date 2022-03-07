package com.hanmajid.android.snowcone.splashscreen

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.BounceInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@RequiresApi(Build.VERSION_CODES.S)
class MainActivity : AppCompatActivity() {
    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handles the splash screen transition
        splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Uncomment the line below if you want to use custom exit animation.
        // useCustomExitAnimation()

        // 2. Uncomment the line below if you want to keep splash screen on-screen for longer period.
        // keepSplashScreenFor5Seconds()

        // 3. Uncomment the line below if you want to keep splash screen on-screen indefinitely.
        // keepSplashScreenIndefinitely()
    }

    /**
     * Use customize exit animation for splash screen.
     */
    private fun useCustomExitAnimation() {
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            val splashScreenView = splashScreenViewProvider.view
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.height.toFloat(),
            )
            slideUp.interpolator = BounceInterpolator()
            slideUp.duration = 1000L
            slideUp.doOnEnd {
                splashScreenViewProvider.remove()
            }
            slideUp.start()
        }
    }

    /**
     * Keep splash screen on-screen indefinitely. This is useful if you're using a custom Activity
     * for routing.
     */
    private fun keepSplashScreenIndefinitely() {
        splashScreen.setKeepOnScreenCondition { true }
    }

    /**
     * Keep splash screen on-screen for longer period. This is useful if you need to load data when
     * splash screen is appearing.
     */
    private fun keepSplashScreenFor5Seconds() {
        val content = findViewById<View>(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                Thread.sleep(5000)
                content.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }
}