package com.example.helthio.view

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.helthio.R
import com.example.helthio.viewmodel.FigmaViewModel
import com.example.helthio.viewmodel.bindButtonData
import com.example.helthio.viewmodel.bindImageData
import com.example.helthio.viewmodel.bindNodeTextData


class MainActivity : AppCompatActivity() {
    private val figmaViewModel: FigmaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Find your UI elements
        val loginButton: Button = findViewById(R.id.loginButton)
        val signUpButton: Button = findViewById(R.id.signUpButton)
        val logoImage: ImageView = findViewById(R.id.logoImageView)
        val logoTextView: TextView = findViewById(R.id.appNameTextView)
        val descriptionTextView: TextView = findViewById(R.id.welcomeTextView)
        val subDescriptionTextView: TextView = findViewById(R.id.subtextTextView)

        // Fetch the image and node data from the API
        figmaViewModel.fetchImageData("1409:1627")   // Logo image node ID
        figmaViewModel.fetchNodeData("1409:1628")    // App name text node ID
        figmaViewModel.fetchNodeData("1409:1624")    // Description text node ID
        figmaViewModel.fetchNodeData("1409:1623")    // Sub-description text node ID
        figmaViewModel.fetchNodeData("1409:1620") // Node ID for Login Button
        figmaViewModel.fetchNodeData("1409:1617") // Node ID for Sign Up Button
        // Bind the image data to ImageView
        bindImageData(
            viewModel = figmaViewModel,
            context = this,
            logoImage = logoImage,
            nodeId = "1409:1627"
        )

        // Bind the node data (for text) to TextView
        bindNodeTextData(
            viewModel = figmaViewModel,
            context = this,
            textView = logoTextView,
            nodeId = "1409:1628"
        )
        bindNodeTextData(
            viewModel = figmaViewModel,
            context = this,
            textView = descriptionTextView,
            nodeId = "1409:1624"
        )
        bindNodeTextData(
            viewModel = figmaViewModel,
            context = this,
            textView = subDescriptionTextView,
            nodeId = "1409:1623"
        )
        bindButtonData(figmaViewModel, this, loginButton, "1409:1620") {
            startActivity(Intent(this, LoginScreen::class.java))
        }
        bindButtonData(figmaViewModel, this, signUpButton, "1409:1617") {
            startActivity(Intent(this, SignupScreen::class.java))
        }
    }
}



