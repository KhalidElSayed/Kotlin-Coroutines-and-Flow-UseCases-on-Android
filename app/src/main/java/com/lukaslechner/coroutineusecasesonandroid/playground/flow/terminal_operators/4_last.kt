package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last


suspend fun main() {
    val flow = flow {
        delay(100)
        println("emitting first value")
        emit(1)
        delay(100)
        println("emitting second value")
        emit(2)
    }.last()

    println("received item: $flow")
}