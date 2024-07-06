package com.calcdistanceapp.data

import com.calcdistanceapp.data.DataResult.Success.FetchLocal

sealed class DataResult<out T : Any> {

    sealed class Success<T : Any>(open val data: T) : DataResult<T>() {

        data class FetchLocal<T : Any>(override val data: T) : Success<T>(data)
        data class FetchRemote<T : Any>(override val data: T) : Success<T>(data)
    }

    data class Error(val msg: String) : DataResult<Nothing>()

    fun isSuccess(): Boolean {
        return this is Success
    }

    fun isFetchLocal(): Boolean {
        return this is FetchLocal
    }

    fun isError(): Boolean {
        return this is Error
    }
}
