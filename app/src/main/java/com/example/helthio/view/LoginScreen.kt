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
import android.widget.Toast
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

class LoginScreen : AppCompatActivity() {
    private val figmaViewModel: FigmaViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        // Initialize views
        val emailInputLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val backButton = findViewById<ImageView>(R.id.backButton)
        val loginButton: Button = findViewById(R.id.loginButton)
        val googleSignInButton: Button = findViewById(R.id.googleSignInButton)
        val appleSignInButton: Button = findViewById(R.id.appleSignInButton)
        val facebookSignInButton: Button = findViewById(R.id.facebookSignInButton)
        val emailInputText = findViewById<TextInputEditText>(R.id.emailInputText)
        val passwordInputText = findViewById<TextInputEditText>(R.id.passwordInputText)
        val forgotPasswordText = findViewById<TextView>(R.id.forgotPasswordText)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val loginTitle = findViewById<TextView>(R.id.loginTitle)
        val orText = findViewById<TextView>(R.id.orText)
        val donTHaveAccountText = findViewById<TextView>(R.id.donTHaveAccountText)

        // Set click listeners
        figmaViewModel.fetchImageData("1409:1586")
        figmaViewModel.fetchImageData("1409:1576")
        figmaViewModel.fetchImageData("1409:1565")
        figmaViewModel.fetchImageData("1409:1542")
        figmaViewModel.fetchImageData("1409:1537")
        figmaViewModel.fetchImageData("1409:1529")
        figmaViewModel.fetchNodeData("1409:1590")
        figmaViewModel.fetchNodeData("1409:1580")
        figmaViewModel.fetchNodeData("1409:1574")
        figmaViewModel.fetchNodeData("1409:1554")
        figmaViewModel.fetchNodeData("1409:1546")
        figmaViewModel.fetchNodeData("1409:1524")
        figmaViewModel.fetchNodeData("1409:1547")
        figmaViewModel.fetchNodeData("1409:1550")
        figmaViewModel.fetchNodeData("1409:1541")
        figmaViewModel.fetchNodeData("1409:1528")
        figmaViewModel.fetchNodeData("1409:1536")
        // Bind the image data to ImageView
        bindImageData(
            viewModel = figmaViewModel,
            context = this,
            logoImage = backButton,
            nodeId = "1409:1586"
        )
        // Bind the node data (for text) to TextView
        bindNodeTextData(
            viewModel = figmaViewModel,
            context = this,
            textView = loginTitle,
            nodeId = "1409:1590"
        )
        bindNodeTextData(
            viewModel = figmaViewModel,
            context = this,
            textView = forgotPasswordText,
            nodeId = "1409:1580"
        )
        bindNodeEditTextData(
            viewModel = figmaViewModel,
            context = this,
            textInputLayout = emailInputLayout,
            nodeId = "1409:1574",
            imageUrlNodeId = "1409:1576"
        )
        bindNodeEditTextData(
            viewModel = figmaViewModel,
            context = this,
            textInputLayout = passwordInputLayout,
            nodeId = "1409:1554",
            imageUrlNodeId = "1409:1565"
        )
        bindNodeTextData(
            viewModel = figmaViewModel,
            context = this,
            textView = orText,
            nodeId = "1409:1524"
        )
        bindNodeTextData(
            viewModel = figmaViewModel,
            context = this,
            textView = donTHaveAccountText,
            nodeId = "1409:1546"
        )
        bindButtonData(figmaViewModel, this, loginButton, "1409:1550") {
            val username = emailInputText.text.toString()
            val password = passwordInputText.text.toString()
            if (verifyLogin(username, password)) {
                startActivity(Intent(this, HomeScreen::class.java))
                finish() // Close the login activity
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
        bindButtonData(figmaViewModel, this, signUpButton, "1409:1547") {
            startActivity(Intent(this, SignupScreen::class.java))
        }
        bindButtonData(figmaViewModel, this, facebookSignInButton, "1409:1541", "1542") {}
        bindButtonData(figmaViewModel, this, googleSignInButton, "1409:1528", "1529") {}
        bindButtonData(figmaViewModel, this, appleSignInButton, "1409:1536", "1537") {}
    }
    private fun verifyLogin(username: String, password: String): Boolean {
        val savedUsername = sharedPreferences.getString("username", "")
        val savedPassword = sharedPreferences.getString("password", "")

        return username == savedUsername && password == savedPassword
    }
}
