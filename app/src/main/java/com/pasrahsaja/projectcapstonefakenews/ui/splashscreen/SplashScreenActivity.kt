package com.pasrahsaja.projectcapstonefakenews.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.pasrahsaja.projectcapstonefakenews.R
import com.pasrahsaja.projectcapstonefakenews.ui.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val test: ImageView = findViewById(R.id.sc_img_news)
        test.alpha = 1F
        test.animate().setDuration(1000).alpha(1f).withEndAction {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }
    }
}