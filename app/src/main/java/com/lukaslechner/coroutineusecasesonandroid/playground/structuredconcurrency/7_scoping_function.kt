package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() {

    val scope = CoroutineScope(Job())

    scope.launch {

        doSomeTasks()

        launch {
            println("Starting Task 3")
            delay(300)
            println("Task 3 Completed")
        }
    }

    Thread.sleep(1000)
}

suspend fun doSomeTasks() =
    coroutineScope {
        launch {
            println("Starting Task 1")
            delay(100)
            println("Task 1 Completed")
        }

        launch {
            println("Starting Task 2")
            delay(200)
            println("Task 2 Completed")
        }
    }
