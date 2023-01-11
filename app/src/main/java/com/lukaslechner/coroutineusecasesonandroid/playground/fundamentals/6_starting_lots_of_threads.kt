package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlin.concurrent.thread

fun main() {
    repeat(1000000) {
        thread {
            Thread.sleep(500)
            print(".")
        }
    }
}