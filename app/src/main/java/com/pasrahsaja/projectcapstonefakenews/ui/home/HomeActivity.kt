package com.pasrahsaja.projectcapstonefakenews.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.pasrahsaja.projectcapstonefakenews.R
import com.pasrahsaja.projectcapstonefakenews.databinding.ActivityHomeBinding
import com.google.tflite.fakenews.Classifier
import com.google.tflite.fakenews.MainActivity
import android.util.Log

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val mModelPath = "fakenews.tflite"
    private val mLabelPath = "label.txt"
    private val mInputSize = 1
    private lateinit var mainactivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainactivity = MainActivity()

        binding.hmBtnNews.setOnClickListener {
//            Toast.makeText(this@HomeActivity, binding.hmInputNews.text, Toast.LENGTH_SHORT).show()
            val inputEditText = binding.hmInputNews.text.toString()
            val result = mainactivity.classify(inputEditText);
            Toast.makeText(this@HomeActivity, result.toString(), Toast.LENGTH_SHORT).show()
        }

    }
}