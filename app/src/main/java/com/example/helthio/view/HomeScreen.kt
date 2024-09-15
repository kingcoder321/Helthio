package com.example.helthio.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.helthio.R

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val notificationButton = findViewById<Button>(R.id.notificationButton)
        notificationButton.setOnClickListener {
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show()
        }

        // Add click listeners for specialty items
        val specialties = listOf("Orthopaedics", "Neurology", "Pathology", "Ophthalmology", "Psychiatry", "Radiology")
        specialties.forEach { specialty ->
            findViewById<View>(resources.getIdentifier(specialty.toLowerCase(), "id", packageName))?.setOnClickListener {
                Toast.makeText(this, "$specialty clicked", Toast.LENGTH_SHORT).show()
            }
        }

        // Add click listener for "Learn more" button
        findViewById<Button>(R.id.learnMoreButton).setOnClickListener {
            Toast.makeText(this, "Learn more clicked", Toast.LENGTH_SHORT).show()
        }
    }
}