package com.geekster.coroutines_1

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity4 : AppCompatActivity() {

    private val TAG : String = "LEARNCOROUTINES"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main4)

        GlobalScope.launch(Dispatchers.Main) {
            checkLongRunningTask()
        }
    }

    private suspend fun execute() {
        val parentJob = GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, "Parent Job started")


            val childJob = launch{          //inherits the context of the parent
                Log.d(TAG, "Child Job started")
                delay(5000)
                Log.d(TAG, "Child Job Ended")
            }

//            val childJobExplicit = launch (Dispatchers.IO){          //inherits the context of the parent
//                Log.d(TAG, "Child - $coroutineContext")
//            }

            delay(3000)
            Log.d(TAG, "Parent Job Ended")
        }

        parentJob.join()
        Log.d(TAG, "Parent Job Completed")
    }

    //Output of the execute() function -
    /*

    Parent Job started
    Child Job started
    Parent Job Ended
    Child Job Ended
    Parent Job Completed

     */


    private suspend fun cancelExecute() {
        val parentJob = GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, "Parent Job started")


            val childJob = launch{          //inherits the context of the parent
                Log.d(TAG, "Child Job started")
                delay(5000)
                Log.d(TAG, "Child Job Ended")
            }

//            val childJobExplicit = launch (Dispatchers.IO){          //inherits the context of the parent
//                Log.d(TAG, "Child - $coroutineContext")
//            }


//          Cancel the child job

            delay(3000)
            childJob.cancel()
            Log.d(TAG, "Parent Job Ended")

            //Output -
            /*
            Parent Job started
            Child Job started
            Parent Job Completed
             */
        }

        //Cancel the parent job
        delay(1000)
        parentJob.cancel()



        parentJob.join()
        Log.d(TAG, "Parent Job Completed")
    }

    //Output of the cancelExecute() function -
    /*
    Parent Job started
    Child Job started
    Parent Job Completed
     */

    private suspend fun checkLongRunningTask() {
        val parentJob = GlobalScope.launch(Dispatchers.IO) {
            for (i in 1..1000){
/*
For an extremely long running task, sometimes the thread gets busy in executing the task
so even if the coroutine is cancelled, the task keeps on running and after a while gets cancelled.
To avoid this we need to check whether the coroutine is active or not using isActive()
 */
                if (isActive) {
                    executeLongRunningTask()
                    Log.d(TAG, "$i")
                }
            }
        }

        delay(100)
        Log.d(TAG, "Cancelling Job")
        parentJob.cancel()
        parentJob.join()
        Log.d(TAG, "Parent job Completed")
    }

    private fun executeLongRunningTask() {
        for (i in 1..10000000) {

        }
    }
}