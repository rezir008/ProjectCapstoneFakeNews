package com.pasrahsaja.projectcapstonefakenews.ui.splashscreen

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.ProgressBar
import com.pasrahsaja.projectcapstonefakenews.R
import com.pasrahsaja.projectcapstonefakenews.databinding.ActivitySplashScreenBinding
import com.pasrahsaja.projectcapstonefakenews.ui.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {
    private val TIME: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val test: ProgressBar = findViewById(R.id.progressbar_sc)
        test.max = 100
        val currentProgress = 100
        ObjectAnimator.ofInt(test, "progress", currentProgress)
            .setDuration(3000)
            .start()

        Handler().postDelayed({
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }, TIME)


    }
}