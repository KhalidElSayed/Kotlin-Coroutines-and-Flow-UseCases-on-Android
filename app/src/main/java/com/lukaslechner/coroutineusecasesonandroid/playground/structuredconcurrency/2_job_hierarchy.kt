package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() {
    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default + scopeJob)

    var childCoroutineJob: Job? = null
    val coroutineJob = scope.launch {
        childCoroutineJob = launch {
            println("starting the child coroutine")
            delay(1000)
            println("stop the child coroutine")
        }
        println("starting the coroutine")
        delay(1000)
        println("stop the coroutine")
    }

    Thread.sleep(1000)

    println("scopeJob.children: => ${scopeJob.children.count()}")
    println("coroutineJob.children: => ${coroutineJob.children.count()}")

    println("Is coroutineJob is a child of scopeJob? => ${scopeJob.children.contains(coroutineJob)}")
    println("Is childCoroutineJob is a child of coroutineJob? => ${coroutineJob.children.contains(childCoroutineJob)}")
    println("Is childCoroutineJob is a child of child of scopeJob? => ${scopeJob.children.elementAt(0).children.contains(childCoroutineJob)}")
}