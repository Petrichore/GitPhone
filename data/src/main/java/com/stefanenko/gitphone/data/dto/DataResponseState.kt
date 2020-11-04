package com.stefanenko.gitphone.data.dto

sealed class DataResponseState<T> {
    data class Data<T>(val data: T) : DataResponseState<T>()
    data class Error<T>(val error: String) : DataResponseState<T>()
    class InProgress<T> : DataResponseState<T>()
}