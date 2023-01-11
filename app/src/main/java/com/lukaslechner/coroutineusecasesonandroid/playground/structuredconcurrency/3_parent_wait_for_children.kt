package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() = runBlocking {
    val scope = CoroutineScope(Dispatchers.Default)

    val parentJob = scope.launch {
        launch {
            delay(1000)
            println("child coroutine 1 has been completed.")
        }

        launch {
            delay(1000)
            println("child coroutine 2 has been completed.")
        }
    }

    parentJob.join()
    println("parent coroutine has been completed.")
}