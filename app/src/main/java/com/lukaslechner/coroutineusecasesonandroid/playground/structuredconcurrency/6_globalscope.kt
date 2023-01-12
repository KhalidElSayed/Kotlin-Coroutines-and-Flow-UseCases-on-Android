package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() {

    println("Job of globalScope ${GlobalScope.coroutineContext[Job]}")

    GlobalScope.launch {

    }
}