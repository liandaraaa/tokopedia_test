package com.tokopedia.core

sealed class Result<T> {

    data class Loading<T>(val data: T?=null):Result<T>()

    data class Empty<T>(val data: T?=null):Result<T>()

    data class Success<T>(val data: T) : Result<T>()

    data class Error<T>(val throwable: Throwable) : Result<T>()

}