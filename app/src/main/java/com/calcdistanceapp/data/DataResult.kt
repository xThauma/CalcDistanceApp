package com.calcdistanceapp.data

sealed class DataResult<out T : Any> {

    sealed class Success : DataResult<Nothing>() {

        data object FetchLocal : Success()
        data object FetchRemote : Success()
    }

    sealed class Error(val msg: String) : DataResult<Nothing>() {
        data object NoInternetError : Error("No internet connection and no local data available")
        data class UnknownError(val exception: Exception) :
            Error(exception.message ?: "Unknown error occurred when fetching station keywords")
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
