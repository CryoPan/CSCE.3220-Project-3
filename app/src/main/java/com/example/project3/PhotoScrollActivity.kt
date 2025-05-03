package com.example.project3

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color

class PhotoScrollActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    private val imageIds = arrayOf(
        R.drawable.geto,
        R.drawable.geto_pretty_boi,
        R.drawable.chibi_okrun
    )

    private var currentIndex = 0

    private var imageWidth = 0

    private var likedImages = mutableSetOf<Int>()
    private var dislikedImages = mutableSetOf<Int>()


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentIndex", currentIndex)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_scroller)

        val imageView = findViewById<ImageView>(R.id.viewPhoto)
        val nextButton = findViewById<ImageButton>(R.id.nextPhoto)
        val backButton = findViewById<ImageButton>(R.id.backPhoto)
        val backToMainButton = findViewById<Button>(R.id.backButton)
        backToMainButton.setOnClickListener {
            finish()
        }

        val likeButton = findViewById<ImageButton>(R.id.likeButton)
        val dislikeButton = findViewById<ImageButton>(R.id.dislikeButton)

        currentIndex = savedInstanceState?.getInt("currentIndex") ?: 0
        imageView.setImageResource(imageIds[currentIndex])

        imageView.viewTreeObserver.addOnGlobalLayoutListener {
            if (imageWidth == 0) {
                imageWidth = imageView.width
            }
        }

        fun updateReactionButtons() {
            val imageId = imageIds[currentIndex]
            likeButton.setColorFilter(
                if (likedImages.contains(imageId)) Color.GREEN else Color.WHITE
            )
            dislikeButton.setColorFilter(
                if (dislikedImages.contains(imageId)) Color.RED else Color.WHITE
            )
        }

        likeButton.setOnClickListener {
            val imageId = imageIds[currentIndex]
            if (likedImages.contains(imageId)) {
                likedImages.remove(imageId)
            } else {
                likedImages.add(imageId)
                dislikedImages.remove(imageId)
            }
            updateReactionButtons()
        }

        dislikeButton.setOnClickListener {
            val imageId = imageIds[currentIndex]
            if (dislikedImages.contains(imageId)) {
                dislikedImages.remove(imageId)
            } else {
                dislikedImages.add(imageId)
                likedImages.remove(imageId)
            }
            updateReactionButtons()
        }


        nextButton.setOnClickListener {
            imageView.animate()
                .translationX(-imageWidth.toFloat())
                .alpha(0f)
                .setDuration(200)
                .withEndAction {
                    currentIndex = (currentIndex + 1) % imageIds.size
                    updateReactionButtons()
                    imageView.translationX = imageWidth.toFloat()
                    imageView.setImageResource(imageIds[currentIndex])
                    imageView.animate()
                        .translationX(0f)
                        .alpha(1f)
                        .setDuration(200)
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .start()
                }
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }

        backButton.setOnClickListener {
            imageView.animate()
                .translationX(imageWidth.toFloat())
                .alpha(0f)
                .setDuration(200)
                .withEndAction {
                    currentIndex = if (currentIndex - 1 < 0) imageIds.size - 1 else currentIndex - 1
                    updateReactionButtons()
                    imageView.translationX = -imageWidth.toFloat()
                    imageView.setImageResource(imageIds[currentIndex])
                    imageView.animate()
                        .translationX(0f)
                        .alpha(1f)
                        .setDuration(200)
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .start()
                }
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }
    }
}
