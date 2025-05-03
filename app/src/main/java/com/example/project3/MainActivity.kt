package com.example.project3

import android.annotation.SuppressLint
import android.os.Bundle
import android.content.Intent
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val landingScreen = findViewById<ConstraintLayout>(R.id.landingScreen)
        landingScreen.setOnClickListener{
            val intent = Intent(this, PhotoScrollActivity::class.java)
            startActivity(intent)
        }

        val infoButton = findViewById<ImageButton>(R.id.infoButton)

        infoButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("About Photo Scroll")
                .setMessage(
                    "Photo Scroll lets you browse a preset of images. \n\n" +
                            "- Tap the screen anywhere to continue to the main application\n" +
                            "- Use arrows to scroll photos\n" +
                            "- Like or dislike each photo\n\n" +
                            "Created by Andres Montoya, 2025."
                )
                .setPositiveButton("OK", null)
                .show()
        }

    }
}