package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() = runBlocking {
    val scope = CoroutineScope(Dispatchers.Default)

    scope.coroutineContext[Job]!!.invokeOnCompletion { cause ->
        if (cause is CancellationException)
            println("parent job was cancelled.")
    }

    val c1Job = scope.launch {
        delay(1000)
        println("coroutine 1 completed.")
    }

    c1Job.invokeOnCompletion { cause ->
        if (cause is CancellationException)
            println("coroutine 1 was cancelled.")
    }

    val c2Job = scope.launch {
        delay(1000)
        println("coroutine 2 completed.")
    }

    c2Job.invokeOnCompletion { cause ->
        if (cause is CancellationException)
            println("coroutine 2 was cancelled.")
    }


    //scope.cancel()
    //scope.coroutineContext[Job]!!.join()

    // this is a shorthand fun for cancelling the job and join it
//    scope.coroutineContext[Job]!!.cancelAndJoin()

    delay(200)
    c1Job.cancelAndJoin()
}