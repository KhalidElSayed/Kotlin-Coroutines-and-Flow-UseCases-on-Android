package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.*
import timber.log.Timber

class TimeoutAndRetryViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading
        val numberOfRetries = 2
        val timeout = 1000L

        // TODO: Exercise 3
        // switch to branch "coroutine_course_full" to see solution

        // run api.getAndroidVersionFeatures(27) and api.getAndroidVersionFeatures(28) in parallel

        val features27 = viewModelScope.async {
            retryWithTimeout(numberOfRetries, timeout) {
                api.getAndroidVersionFeatures(27)
            }
        }
        val features28 = viewModelScope.async {
            retryWithTimeout(numberOfRetries, timeout) {
                api.getAndroidVersionFeatures(28)
            }
        }

        viewModelScope.launch {
            try {
                val versionFeatures = listOf(features27, features28).awaitAll()
                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed!")
            }
        }
    }

    private suspend fun <T> retryWithTimeout(
        numberOfRetries: Int,
        timeout: Long,
        block: suspend () -> T
    ) = retry(numberOfRetries) {
        withTimeout(timeout) { block() }
    }

    private suspend fun <T> retry(
        numberOfRetries: Int,
        delayBetweenRetries: Long = 100,
        block: suspend () -> T
    ): T {
        repeat(numberOfRetries) {
            try {
                return block()
            } catch (e: Exception) {
                Timber.e(e)
            }
            delay(delayBetweenRetries)
        }
        return block()
    }
}