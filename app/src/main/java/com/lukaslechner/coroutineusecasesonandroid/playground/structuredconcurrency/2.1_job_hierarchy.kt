package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() {
    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default + scopeJob)

    val coroutineJob = scope.launch {
        println("starting the coroutine")
        delay(1000)
        println("stop the coroutine")
    }

    Thread.sleep(1000)

    println("scopeJob.children: => ${scopeJob.children.count()}")
    println("coroutineJob.children: => ${coroutineJob.children.count()}")

    println("Is coroutineJob is a child of scopeJob? => ${scopeJob.children.contains(coroutineJob)}")
}