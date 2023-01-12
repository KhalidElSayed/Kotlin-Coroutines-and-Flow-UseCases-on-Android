package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CalculationInBackgroundViewModel : BaseViewModel<UiState>() {

    fun performCalculation(factorialOf: Int) {
        viewModelScope.launch {
            try {
                var result: BigInteger
                var resultString: String
                val computationDuration: Long
                val stringConversionDuration: Long

                uiState.value = UiState.Loading

                Timber.d("coroutine context is $coroutineContext")
                computationDuration = measureTimeMillis {
                    result = calculatingFactorial(factorialOf)
                }
                stringConversionDuration = measureTimeMillis {
                    resultString = withContext(Dispatchers.Default) {
                        result.toString()
                    }
                }

                uiState.value = UiState.Success(resultString, computationDuration, stringConversionDuration)
            } catch (e: Exception) {
                uiState.value = UiState.Error("conversion error $e")
            }
        }
    }

    private suspend fun calculatingFactorial(factorialOf: Int): BigInteger {
        // TODO: this code will lead to memory leak because the running operation here inside
        //  the coroutine will keep running in sipte of
        return withContext(Dispatchers.Default) {
            var factorial = BigInteger.ONE
            for (i in 1..factorialOf) {
                factorial = factorial.multiply(i.toBigInteger())
            }
            factorial
        }
    }
}