package com.geekster.coroutines_1

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield


class MainActivity2 : AppCompatActivity() {


    private val TAG : String = "Thread"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        CoroutineScope(Dispatchers.Main).launch {
            task1()
        }
        CoroutineScope(Dispatchers.Main).launch {
            task2()
        }

    }

    suspend fun task1() {
        Log.d(TAG, "task1 STARTED")
        yield()
        Log.d(TAG, "task1 FINISHED")
    }

    suspend fun task2() {
        Log.d(TAG, "task2 STARTED")
        yield()
        Log.d(TAG, "task2 FINISHED")
    }


}