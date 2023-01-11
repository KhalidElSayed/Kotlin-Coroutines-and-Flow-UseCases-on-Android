package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.rx

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class SequentialNetworkRequestsRxViewModel(
    private val mockApi: RxMockApi = mockApi()
) : BaseViewModel<UiState>() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun perform2SequentialNetworkRequest() {
        mockApi.getRecentAndroidVersions()
            .flatMap { mockApi.getAndroidVersionFeatures(it.last().apiLevel) }
            .doOnSubscribe { uiState.value = UiState.Loading }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { versions -> uiState.value = UiState.Success(versions) },
                { uiState.value = UiState.Error(it.message.toString()) }
            )
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}