package com.geekster.coroutines_1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val counterText = findViewById<TextView>(R.id.counter)
        val counterBtn = findViewById<Button>(R.id.updateCounter)

        Log.d("Thread", "onCreate: ${Thread.currentThread().name}")

        counterBtn.setOnClickListener {
            Log.d("Thread", "onCreate: ${Thread.currentThread().name}")
            var counter = counterText.text.toString().toInt()
            counter++
            counterText.text = counter.toString()
        }

    }

//    private fun longRunningTask() {
//        for (i in 1..10000000000L) {
//            // Do nothing
//        }
//    }

    fun incrementCounter(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Thread", "1: ${Thread.currentThread().name}")
        }

        GlobalScope.launch(Dispatchers.Main) {
            Log.d("Thread", "2: ${Thread.currentThread().name}")
        }

        MainScope().launch(Dispatchers.Default) {
            Log.d("Thread", "3: ${Thread.currentThread().name}")
        }
    }
}