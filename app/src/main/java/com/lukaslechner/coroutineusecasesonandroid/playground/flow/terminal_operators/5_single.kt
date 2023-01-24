package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single


suspend fun main() {
    // single() is a terminal operator that used to received exactly one item from the flow
    // if used flow emitting more than one item it'll throws 'IllegalArgumentException'
    // and if the flow doesn't emit any item it'll throws 'NoSuchElementException'
    val flow = flow<Int> {
        delay(100)
        println("emitting first value")
        emit(1)
        /*delay(100)
        println("emitting second value")
        emit(2)*/
    }.single()

    println("received item: $flow")
}