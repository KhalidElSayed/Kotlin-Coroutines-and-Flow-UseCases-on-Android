package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("caught exception: $throwable")
    }

    val scope = CoroutineScope(SupervisorJob() + exceptionHandler)

    scope.launch {
        println("coroutine 1 start")
        delay(50)
        println("coroutine 1 fails")
        throw RuntimeException()
    }

    scope.launch {
        println("coroutine 2 start")
        delay(500)
        println("coroutine 2 completed")
    }.invokeOnCompletion { cause ->
        if (cause is CancellationException)
            println("coroutine 2 got cancelled")
    }

    Thread.sleep(1000)

    println("Is the scope still active? ${scope.isActive}")
}