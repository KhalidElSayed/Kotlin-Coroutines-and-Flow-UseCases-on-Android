package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { coroutine(1, 500) },
        async { coroutine(2, 300) }
    )
    /*listOf(
        async { coroutine(1, 500) },
        async { coroutine(2, 300) }
    ).awaitAll()*/
    println("main ends")
}

suspend fun coroutine(number: Int, delay: Long) {
    println("coroutine $number starts work")
    delay(delay)
    println("coroutine $number has finished")
}