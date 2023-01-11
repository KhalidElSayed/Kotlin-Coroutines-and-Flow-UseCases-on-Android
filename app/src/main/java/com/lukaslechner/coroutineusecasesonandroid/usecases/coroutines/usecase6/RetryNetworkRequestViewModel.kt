package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class RetryNetworkRequestViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        viewModelScope.launch {
            val numberOfRetries = 2
            uiState.value = UiState.Loading
            try {
                retry(numberOfRetries) { loadRecentAndroidVersions() }
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed!")
            }
        }
    }

    private suspend fun retry(
        numberOfRetries: Int,
        initialDelayMillis: Long = 100,
        maxDelayMillis: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> Unit
    ) {
        var currentDelay = initialDelayMillis
        repeat(numberOfRetries) {
            try {
                Timber.d("try request $it")
                return block()
            } catch (e: Exception) {
                Timber.e(e)
            }
            Timber.d("retry request delayed ${currentDelay}ms")
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
        }
        Timber.d("try request after repeat")
        return block()
    }

    private suspend fun loadRecentAndroidVersions() {
        uiState.value = UiState.Success(api.getRecentAndroidVersions())
    }
}