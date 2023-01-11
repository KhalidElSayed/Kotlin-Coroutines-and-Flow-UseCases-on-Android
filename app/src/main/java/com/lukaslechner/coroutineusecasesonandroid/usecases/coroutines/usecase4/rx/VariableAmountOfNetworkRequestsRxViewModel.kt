package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4.rx

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class VariableAmountOfNetworkRequestsRxViewModel(
    private val mockApi: RxMockApi = mockApi()
) : BaseViewModel<UiState>() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun performNetworkRequestsSequentially() {
        mockApi.getRecentAndroidVersions()
            .toObservable()
            .flatMapIterable { it }
            .concatMapSingle { mockApi.getAndroidVersionFeatures(it.apiLevel) }
            .toList()
            .doOnSubscribe { uiState.value = UiState.Loading }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { versions -> uiState.value = UiState.Success(versions) },
                { uiState.value = UiState.Error(it.message.toString()) }
            )
            .addTo(disposables)
    }

    fun performNetworkRequestsConcurrently() {
        mockApi.getRecentAndroidVersions()
            .toObservable()
            .flatMapIterable { it }
            .flatMapSingle { mockApi.getAndroidVersionFeatures(it.apiLevel) }
            .toList()
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