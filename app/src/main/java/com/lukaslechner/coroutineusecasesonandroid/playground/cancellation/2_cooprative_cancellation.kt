package com.lukaslechner.coroutineusecasesonandroid.playground

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(10) {
            //TODO: to make our coroutine code cooperative cancellable use one of the following:
            // 1: call 'ensureActive()' at the first line of the coroutine code.
            // 2: call 'yield()' also at the first line of the coroutine code.

            //ensureActive()
            // or
            //yield()
            // or
            if (isActive) {
                println("operation number #$it")
                Thread.sleep(100)
            } else {
                //TODO: here we call our code inside the 'withContext()' block with a special Job
                // called 'NonCancellable' to be able to run a suspend function and other code that
                // follows it. also we need to throw the 'CancellationException' again at the end to complete
                // our coroutine exceptionally.
                withContext(NonCancellable) {
                    delay(100)
                    println("cleanup code...")
                    throw CancellationException()
                }
            }
        }
    }

    delay(250)
    println("cancelling the coroutine")
    job.cancel()
}