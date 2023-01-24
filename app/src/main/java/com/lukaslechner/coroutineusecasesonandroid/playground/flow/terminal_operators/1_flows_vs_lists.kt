package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow


fun main() {
    val flow = flow {
        delay(100)
        println("emitting first value")
        emit(1)
        delay(100)
        println("emitting second value")
        emit(2)
    }

    val list = buildList {
        println("adding the first value")
        add(1)
        println("adding the second value")
        add(2)
    }
}