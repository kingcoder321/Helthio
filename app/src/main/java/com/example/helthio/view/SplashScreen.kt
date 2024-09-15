package com.example.helthio.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import coil.load
import com.example.helthio.R
import com.example.helthio.viewmodel.FigmaViewModel


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private val SPLASH_SCREEN_TIME: Long = 3000 // 3 seconds
    private val figmaViewModel: FigmaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        // Get reference to the ImageView
        val splashLogo = findViewById<ImageView>(R.id.splash_logo)

        // Fetch image from Figma and load into ImageView
        fetchSplashImage(figmaViewModel = figmaViewModel, splashLogo = splashLogo, context = this)

        // Delay for 3 seconds and transition to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close SplashScreen activity
        }, SPLASH_SCREEN_TIME)

        figmaViewModel.fetchImageData("1409:1651")
    }
}

private fun fetchSplashImage(
    figmaViewModel: FigmaViewModel,
    splashLogo: ImageView,
    context: LifecycleOwner
) {
    figmaViewModel.figmaImageResponse.observe(context) { response ->
        if (response != null) {
            Log.d("FigmaAPI", "Document Name: ${response.images}")
            val imageUrl = response.images.values.firstOrNull()
            splashLogo.load(imageUrl) {
                crossfade(true)
                placeholder(R.drawable.splash_logo) // Replace with your placeholder image
            }
        } else {
            Log.d("FigmaAPI", "Failed to get response from Figma API.")
        }
    }
}