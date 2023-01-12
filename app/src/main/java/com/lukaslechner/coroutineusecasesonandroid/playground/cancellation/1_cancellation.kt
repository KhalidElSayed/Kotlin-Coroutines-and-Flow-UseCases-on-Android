package com.lukaslechner.coroutineusecasesonandroid.playground

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = launch {
        repeat(10) {
            println("operation number #$it")
            try {
                delay(100)
            } catch (e: CancellationException) {
                println("CancellationException was thrown!!")
                throw CancellationException()
            }
        }
    }

    delay(250)
    println("cancelling the coroutine")
    job.cancel()
}