package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase8

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch

class RoomAndCoroutinesViewModel(
    private val api: MockApi,
    private val database: AndroidVersionDao
) : BaseViewModel<UiState>() {

    fun loadData() {
        viewModelScope.launch {
            uiState.value = UiState.Loading.LoadFromDb
            database.getAndroidVersions().let {
                if (it.isEmpty()) {
                    uiState.value = UiState.Error(DataSource.DATABASE, "database is empty.")
                } else {
                    uiState.value = UiState.Success(DataSource.DATABASE, it.mapToUiModelList())
                }
                try {
                    uiState.value = UiState.Loading.LoadFromNetwork
                    val androidVersionsApi = api.getRecentAndroidVersions()
                    androidVersionsApi.forEach { database.insert(it.mapToEntity()) }
                    uiState.value = UiState.Success(DataSource.NETWORK, androidVersionsApi)
                } catch (e: Exception) {
                    uiState.value = UiState.Error(DataSource.NETWORK, "something went wrong!")
                }
            }
        }
    }

    fun clearDatabase() {
        viewModelScope.launch { database.clear() }
    }
}

enum class DataSource(val dataSourceName: String) {
    DATABASE("Database"),
    NETWORK("Network")
}