package com.geekster.coroutines_1

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity3 : AppCompatActivity() {

    private val TAG : String = "LEARNCOROUTINES"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        CoroutineScope(Dispatchers.IO).launch {
            printFollowersUsingLaunch()
            printFollowersUsingAsync()
            printFollowersUsingAsyncParallelProcessing()
        }
    }



    private suspend fun printFollowersUsingLaunch() {
        var fbFollowers = 0
        var instaFollowers = 0
        val job = CoroutineScope(Dispatchers.IO).launch{
            fbFollowers = getFbFollowers()
        }

        val job2 = CoroutineScope(Dispatchers.IO).launch{
            instaFollowers = getInstaFollowers()
        }

        job.join()  //Once the coroutine is executed, it'll go to the next line
        job2.join()

        Log.d(TAG, "FB - $fbFollowers, Insta - $instaFollowers")  //It'll print 0 as because of the delay in the getFbFollowers function
        //To avoid this we use the Job class.
    }



    private suspend fun printFollowersUsingAsync() {
        val fbDeferred = CoroutineScope(Dispatchers.IO).async{
            getFbFollowers()        //Deferred is of Generic type -> Here the type is Int
        }
        val instaDeferred = CoroutineScope(Dispatchers.IO).async{
            getInstaFollowers()        //Deferred is of Generic type -> Here the type is Int
        }

        Log.d(TAG, "FB - ${fbDeferred.await()}, Insta - ${instaDeferred.await()}")
    }



    private suspend fun printFollowersUsingAsyncParallelProcessing() {
        val job = CoroutineScope(Dispatchers.IO).launch{
            val fbDeferred = async { getFbFollowers() }        //Deferred is of Generic type -> Here the type is Int
            val instaDeferred = async { getInstaFollowers() }  //Here Parallel processing is done

            Log.d(TAG, "FB - ${fbDeferred.await()}, Insta - ${instaDeferred.await()} ")
        }

    }



    private suspend fun getFbFollowers() : Int {
        delay(1000)
        return 54
    }

    private suspend fun getInstaFollowers() : Int {
        delay(1000)
        return 113
    }
}