package com.example.helthio.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
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
import com.example.helthio.viewmodel.bindNodeEditTextData
import com.example.helthio.viewmodel.bindNodeTextData
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignupScreen : AppCompatActivity() {
    private val figmaViewModel: FigmaViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_screen)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        // Initializing views
        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val nameInputLayout = findViewById<TextInputLayout>(R.id.nameInputLayout)
        val emailInputLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val termsTextView = findViewById<TextView>(R.id.termsTextView)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val logInButton = findViewById<Button>(R.id.logInButton)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val alreadyHaveAccountTextView = findViewById<TextView>(R.id.alreadyHaveAccountTextView)
        val backButton = findViewById<ImageView>(R.id.backButton)

        figmaViewModel.fetchImageData("1409:1495")
        figmaViewModel.fetchImageData("1409:1478")
        figmaViewModel.fetchImageData("1409:1469")
        figmaViewModel.fetchImageData("1409:1459")
        figmaViewModel.fetchNodeData("1409:1497")
        figmaViewModel.fetchNodeData("1409:1476")
        figmaViewModel.fetchNodeData("1409:1468")
        figmaViewModel.fetchNodeData("1409:1448")
        figmaViewModel.fetchNodeData("1409:1483")
        figmaViewModel.fetchNodeData("1409:1444")
        figmaViewModel.fetchNodeData("1409:1440")
        figmaViewModel.fetchNodeData("1409:1441")

        // Bind the image data to ImageView
        bindImageData(
            viewModel = figmaViewModel,
            context = this,
            logoImage = backButton,
            nodeId = "1409:1495"
        )

        // Bind the node data (for text) to TextView
        bindNodeTextData(
            viewModel = figmaViewModel,
            context = this,
            textView = termsTextView,
            nodeId = "1409:1483"
        )
        bindNodeTextData(
            viewModel = figmaViewModel,
            context = this,
            textView = alreadyHaveAccountTextView,
            nodeId = "1409:1440"
        )
        bindNodeTextData(
            viewModel = figmaViewModel,
            context = this,
            textView = titleTextView,
            nodeId = "1409:1497"
        )
        bindNodeEditTextData(
            viewModel = figmaViewModel,
            context = this,
            textInputLayout = nameInputLayout,
            nodeId = "1409:1476",
            imageUrlNodeId = "1409:1478"
        )
        bindNodeEditTextData(
            viewModel = figmaViewModel,
            context = this,
            textInputLayout = emailInputLayout,
            nodeId = "1409:1468",
            imageUrlNodeId = "1409:1469"
        )
        bindNodeEditTextData(
            viewModel = figmaViewModel,
            context = this,
            textInputLayout = passwordInputLayout,
            nodeId = "1409:1448",
            imageUrlNodeId = "1409:1459"
        )
        bindButtonData(figmaViewModel, this, logInButton, "1409:1441") {
            startActivity(Intent(this, LoginScreen::class.java))
        }
        bindButtonData(figmaViewModel, this, signUpButton, "1409:1444") {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            saveUserData(email, password)
            startActivity(Intent(this, HomeScreen::class.java))
        }
    }

    private fun saveUserData(email: String, password: String) {
        with(sharedPreferences.edit()) {
            putString("username", email)
            putString("password", password)
            apply()
        }
    }
}
