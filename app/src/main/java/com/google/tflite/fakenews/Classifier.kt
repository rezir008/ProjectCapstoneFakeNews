package com.google.tflite.fakenews

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*


class Classifier(assetManager: AssetManager, modelPath: String, labelPath: String, inputSize: Int) {
    private var interpreter: Interpreter
    private var lableList: List<String>
    private val MAX_RESULTS = 3
    private val THRESHOLD = 0.4f

    data class Recognition(
        var id: String = "",
        var title: String = "",
        var confidence: Float = 0F
    )  {
        override fun toString(): String {
            return "Title = $title, Confidence = $confidence)"
        }
    }

    init {
        val options = Interpreter.Options()
        options.setNumThreads(5)
        options.setUseNNAPI(true)
        interpreter = Interpreter(loadModelFile(assetManager, modelPath), options)
        lableList = loadLabelList(assetManager, labelPath)
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun loadLabelList(assetManager: AssetManager, labelPath: String): List<String> {
        return assetManager.open(labelPath).bufferedReader().useLines { it.toList() }

    }

    /**
     * Returns the result after running the recognition with the help of interpreter
     * on the passed bitmap
     */
    fun detectNews(input: String): List<Recognition> {
        val result = Array(1) { FloatArray(lableList.size) }
        interpreter.run(input, result)
        return getSortedResult(result)
    }

    private fun getSortedResult(labelProbArray: Array<FloatArray>): List<Classifier.Recognition> {
//        Log.d("Classifier", "List Size:(%d, %d, %d)".format(labelProbArray.size,labelProbArray[0].size,lableList.size))

        val pq = PriorityQueue(
            MAX_RESULTS,
            Comparator<Classifier.Recognition> {
                    (_, _, confidence1), (_, _, confidence2)
                -> java.lang.Float.compare(confidence1, confidence2) * -1
            })

        for (i in lableList.indices) {
            val confidence = labelProbArray[0][i]
            if (confidence >= THRESHOLD) {
                pq.add(Classifier.Recognition("" + i,
                    if (lableList.size > i) lableList[i] else "Unknown", confidence)
                )
            }
        }
        Log.d("Classifier", "pqsize:(%d)".format(pq.size))

        val recognitions = ArrayList<Classifier.Recognition>()
        val recognitionsSize = Math.min(pq.size, MAX_RESULTS)
        for (i in 0 until recognitionsSize) {
            recognitions.add(pq.poll())
        }
        return recognitions
    }
}