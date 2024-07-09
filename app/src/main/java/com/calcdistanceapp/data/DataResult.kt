package com.calcdistanceapp.data

sealed class DataResult<out T : Any> {

    sealed class Success : DataResult<Nothing>() {

        data object FetchLocal : Success()
        data object FetchRemote : Success()
        data object FetchJson : Success()
    }

    sealed class Error : DataResult<Nothing>() {
        data class UnknownError(val exception: Exception) : Error()
    }

    fun isSuccess(): Boolean {
        return this is Success
    }

    fun isFetchRemote(): Boolean {
        return this is Success.FetchRemote
    }

    fun isError(): Boolean {
        return this is Error
    }
}
