package com.pasrahsaja.projectcapstonefakenews.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.pasrahsaja.projectcapstonefakenews.R
import com.pasrahsaja.projectcapstonefakenews.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hmBtnNews.setOnClickListener {
            Toast.makeText(this@HomeActivity, binding.hmInputNews.text, Toast.LENGTH_SHORT).show()
        }

    }
}