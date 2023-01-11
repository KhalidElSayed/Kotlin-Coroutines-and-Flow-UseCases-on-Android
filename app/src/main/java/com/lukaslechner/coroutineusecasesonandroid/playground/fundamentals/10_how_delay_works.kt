package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { delayDemonstration(1, 500) },
        async { delayDemonstration(2, 300) }
    )
//    delayDemonstration(1, 500)
//    delayDemonstration(2, 300)
    println("main ends")
}

suspend fun delayDemonstration(number: Int, delay: Long) {
    println("coroutine $number starts work")
//    delay(delay)
    Handler(Looper.getMainLooper())
        .postDelayed({ println("coroutine $number has finished") }, delay)
}