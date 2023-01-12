package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PerformSingleNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        val job = viewModelScope.launch(Dispatchers.Main) {
            println("the first statement in the coroutine")
            uiState.value = UiState.Loading
            try {
                uiState.value = UiState.Success(mockApi.getRecentAndroidVersions())
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed!")
            }
        }

        println("the first statement after the coroutine")

        job.invokeOnCompletion { cause ->
            if (cause is CancellationException)
            println("coroutine was cancelled")
        }
    }
}