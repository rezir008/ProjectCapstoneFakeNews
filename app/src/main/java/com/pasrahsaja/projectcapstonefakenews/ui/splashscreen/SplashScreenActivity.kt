package com.pasrahsaja.projectcapstonefakenews.ui.splashscreen

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import com.pasrahsaja.projectcapstonefakenews.R
import com.pasrahsaja.projectcapstonefakenews.ui.home.HomeActivity

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    private val TIME: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val test: ProgressBar = findViewById(R.id.progressbar_sc)
        test.max = TIME.toInt()
        val currentProgress = TIME.toInt()
        ObjectAnimator.ofInt(test, "progress", currentProgress)
            .setDuration(TIME)
            .start()

        Handler().postDelayed({
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }, TIME)


    }
}