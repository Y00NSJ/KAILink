package com.example.kailink

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text






class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        splashAnimation()
        // Navigate to ContactActivity after a delay
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, ContactActivity::class.java))
            finish()
        }, 3000) // 1-second delay
    }
    private fun splashAnimation() {
        val splashTextView: ImageView = findViewById(R.id.appName)
        val textAnim : Animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash_text)
        splashTextView.startAnimation(textAnim)
        val splashImageView: ImageView = findViewById(R.id.appLogo)
        val imageAnim : Animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash_image)
        splashImageView.startAnimation(imageAnim)
    }
}