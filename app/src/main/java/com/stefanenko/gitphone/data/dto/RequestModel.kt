package com.stefanenko.gitphone.data.dto

data class RequestModel<T, R>(
    val requestModel: T,
    val responseType: R
)