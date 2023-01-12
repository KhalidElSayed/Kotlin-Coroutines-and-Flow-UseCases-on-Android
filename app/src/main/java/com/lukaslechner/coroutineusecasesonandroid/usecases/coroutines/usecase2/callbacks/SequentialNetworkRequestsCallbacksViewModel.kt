package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.callbacks

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SequentialNetworkRequestsCallbacksViewModel(
    private val mockApi: CallbackMockApi = mockApi()
) : BaseViewModel<UiState>() {

    lateinit var recentAndroidVersions: Call<List<AndroidVersion>>
    lateinit var androidVersionFeatures: Call<VersionFeatures>

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading
        recentAndroidVersions = mockApi.getRecentAndroidVersions()
        recentAndroidVersions.enqueue(object : Callback<List<AndroidVersion>> {
            override fun onResponse(
                call: Call<List<AndroidVersion>>,
                response: Response<List<AndroidVersion>>
            ) {
                if (response.isSuccessful) {
                    androidVersionFeatures =
                        mockApi.getAndroidVersionFeatures(response.body()!!.last().apiLevel)
                    androidVersionFeatures.enqueue(object : Callback<VersionFeatures> {
                        override fun onResponse(
                            call: Call<VersionFeatures>,
                            response: Response<VersionFeatures>
                        ) {
                            if (response.isSuccessful) {
                                uiState.value = UiState.Success(response.body()!!)
                            } else {
                                uiState.value = UiState.Error("Something went wrong!")
                            }
                        }

                        override fun onFailure(call: Call<VersionFeatures>, t: Throwable) {
                            uiState.value = UiState.Error(t.message.toString())
                        }
                    })
                } else {
                    uiState.value = UiState.Error("Something went wrong!")
//                        Log.e("callback-error", response.code().toString())
                }
            }

            override fun onFailure(call: Call<List<AndroidVersion>>, t: Throwable) {
                uiState.value = UiState.Error(t.message.toString())
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        if (this::recentAndroidVersions.isInitialized)
            recentAndroidVersions.cancel()
        if (this::androidVersionFeatures.isInitialized)
            androidVersionFeatures.cancel()
    }
}