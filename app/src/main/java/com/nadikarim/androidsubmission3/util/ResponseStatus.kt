package com.nadikarim.androidsubmission3.util

import retrofit2.Response

data class ResponseStatus<T>(
    val status: Status,
    val data : Response<T>?,
    val exception: Exception?
) {
    sealed class Status {
        object Success : Status()
        object Failure : Status()
    }

    val failed: Boolean
    get() = this.status == Status.Failure

    val isSuccessful: Boolean
    get() = !failed && this.data?.isSuccessful == true

    val body: T
    get() = this.data!!.body()!!

    companion object {
        fun <T> success(data: Response<T>): ResponseStatus<T> {
            return ResponseStatus(
                Status.Success,
                data,
                null
            )
        }

        fun <T> failure(exception: Exception): ResponseStatus<T> {
            return ResponseStatus(
                Status.Success,
                null,
                exception
            )
        }
    }
}
