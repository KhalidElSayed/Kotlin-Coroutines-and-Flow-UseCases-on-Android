package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold


suspend fun main() {
    val flow = flow {
        delay(100)
        println("emitting first value")
        emit(1)
        delay(100)
        println("emitting second value")
        emit(2)
    }

    flow.fold(0, { acc, value -> acc + value })
        .asFlow()
        .collect { items ->
            println("received items using fold: $items")
        }
}

fun Any.asFlow(): Flow<Any> {
    return flow {
        emit(this@asFlow)
    }
}