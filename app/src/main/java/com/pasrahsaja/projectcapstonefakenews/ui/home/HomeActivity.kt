package com.pasrahsaja.projectcapstonefakenews.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel
import com.pasrahsaja.projectcapstonefakenews.R
import com.pasrahsaja.projectcapstonefakenews.databinding.ActivityHomeBinding
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val TAG = "TextClassificationDemo"
    private var executorService: ExecutorService? = null
    private var textClassifier: NLClassifier? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        executorService = Executors.newSingleThreadExecutor()
        downloadModel("fakenews")

        binding.hmBtnNews.setOnClickListener {
            val inputEditText = binding.hmInputNews.text.toString()
            binding.progressbar.visibility = VISIBLE
            classify(inputEditText)
            binding.hmResult.text = inputEditText
            binding.hmBg2.visibility = VISIBLE
            binding.hmResultNews.visibility = VISIBLE
            if(binding.hmResult.text.toString().toDouble()  < binding.hmResultNews.text.toString().toDouble() ){
                result(1)
            }
            else{
                result(0)
            }
            binding.progressbar.visibility = GONE
            Toast.makeText(this@HomeActivity, inputEditText, Toast.LENGTH_SHORT).show()
        }
    }

    private fun result(classify: Int) {

        if(classify == 1){
            binding.hmResultNews.text ="Valid"
            binding.hmImgVerified.visibility = VISIBLE
            binding.hmImgUnverified.visibility = GONE
        }
        else{
            binding.hmResultNews.text ="Invalid"
            binding.hmImgVerified.visibility = GONE
            binding.hmImgUnverified.visibility = VISIBLE
        }
    }

    private fun classify(text: String){
        executorService?.execute {
            val results = textClassifier!!.classify(text)
            binding.hmResult.text = results[0].score.toDouble().toString()
            binding.hmResultNews.text = results[1].score.toDouble().toString()
        }
    }

    @Synchronized
    fun downloadModel(modelName: String?) {
        val remoteModel = FirebaseCustomRemoteModel.Builder(modelName!!)
            .build()
        val conditions = FirebaseModelDownloadConditions.Builder()
            .requireWifi()
            .build()
        val firebaseModelManager = FirebaseModelManager.getInstance()
        firebaseModelManager
            .download(remoteModel, conditions)
            .continueWithTask { task: Task<Void?>? ->
                firebaseModelManager.getLatestModelFile(
                    remoteModel
                )
            }
            .continueWith(
                executorService!!, { task: Task<File> ->
                    val modelFile = task.result
                    textClassifier = NLClassifier.createFromFile(modelFile)
                    null
                })
            .addOnFailureListener { e: Exception? ->
                Log.e(TAG, "Failed to download and initialize the model. ", e)
                Toast.makeText(
                    this@HomeActivity,
                    "Model download failed, please check your connection.",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
    }
}