package com.hanmajid.android.snowcone.roundedcorners

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.RoundedCorner
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.sin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        // Hides the systems bar (status bar & navigation bar).
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        // Get the top-right rounded corner from WindowInsets.
        val view = window.decorView
        val windowInsets = view.rootWindowInsets
        val topRight = windowInsets?.getRoundedCorner(RoundedCorner.POSITION_TOP_RIGHT) ?: return

        // Get the location of the button in window coordinates.
        val location = IntArray(2)
        val button = findViewById<Button>(R.id.button)
        button.getLocationInWindow(location)
        val buttonRightInWindow = location[0] + button.width
        val buttonTopInWindow = location[1]

        // Find the point on the quarter circle with 45 degree angle.
        val offset = (topRight.radius * sin(Math.toRadians(45.0))).toInt()
        val topBoundary = topRight.center.y - offset
        val rightBoundary = topRight.center.x + offset

        // Check whether the close button exceeds the boundary.
        if (buttonRightInWindow < rightBoundary && buttonTopInWindow > topBoundary) {
            return
        }

        // Set the margin to avoid truncating.
        val lp = button.layoutParams as ConstraintLayout.LayoutParams
        lp.rightMargin = (buttonRightInWindow - rightBoundary).coerceAtLeast(0)
        lp.topMargin = (topBoundary - buttonTopInWindow).coerceAtLeast(0)
        button.layoutParams = lp
    }
}