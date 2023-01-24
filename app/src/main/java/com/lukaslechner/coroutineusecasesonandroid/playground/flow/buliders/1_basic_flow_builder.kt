package com.lukaslechner.coroutineusecasesonandroid.playground.flow.buliders

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*


suspend fun main() {
    val firstFlow = flowOf(1)

    firstFlow.collect {
        println("one flowOf: $it")
    }

    val secondFlow = flowOf(1, 2, 3, 4)

    secondFlow.collect {
        println("multiple flowOf: $it")
    }

    val thirdFlow = listOf("A", "B", "C", "D").asFlow()

    thirdFlow.collect {
        println("asFlow: $it")
    }

    flow {
        delay(2000)
        emit("item emitted after 2 seconds.")

        // in flow there is a shorthand for the code below using emitAll(/*ourFlowHere*/)
        /*thirdFlow.collect {
            emit(it)
        }*/
        emitAll(thirdFlow)
    }.collect {
        println("flow: $it")
    }
}