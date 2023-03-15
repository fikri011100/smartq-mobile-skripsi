package com.smartqid.smartq.revamp.common

import retrofit2.Response

data class BaseResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: Exception?
) {

    companion object {
        fun <T> success(data: Response<T>): BaseResponse<T> {
            return BaseResponse(Status.Success, data, null)
        }

        fun <T> failure(exception: Exception?): BaseResponse<T> {
            return BaseResponse(Status.Failure, null, exception)
        }
    }

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
}