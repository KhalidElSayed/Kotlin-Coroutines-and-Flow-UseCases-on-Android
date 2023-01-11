package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

class NetworkRequestWithTimeoutViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeout: Long) {
//        usingWithTimeOut(timeout)
        usingWithTimeOutOrNull(timeout)
    }

    private fun usingWithTimeOut(timeout: Long) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val recentVersions = withTimeout(timeout) { api.getRecentAndroidVersions() }
                uiState.value = UiState.Success(recentVersions)
            } catch (e: TimeoutCancellationException) {
                uiState.value = UiState.Error("Network request timed out!")
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed!")
            }
        }
    }

    private fun usingWithTimeOutOrNull(timeout: Long) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val recentVersions = withTimeoutOrNull(timeout) { api.getRecentAndroidVersions() }
                recentVersions?.let { uiState.value = UiState.Success(recentVersions) }
                    ?: run { uiState.value = UiState.Error("Network request timed out!") }
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed!")
            }
        }
    }
}