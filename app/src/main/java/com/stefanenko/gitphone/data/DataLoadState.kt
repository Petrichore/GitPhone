package com.stefanenko.gitphone.data

sealed class DataLoadState<T> {
    data class Data<T>(val data: T) : DataLoadState<T>()
    data class LoadError<T>(val error: String) : DataLoadState<T>()
    class Loading<T> : DataLoadState<T>()
}