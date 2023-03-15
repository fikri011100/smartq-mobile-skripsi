package com.smartqid.smartq.revamp.utils

data class DataState<T>(val state: State, val data: T? = null, val error: String? = null) {

    enum class State { LOADING, SUCCESS, ERROR, EMPTY }

    companion object {
        fun <T> success(data: T): DataState<T> {
            return DataState(State.SUCCESS, data)
        }

        fun <T> loading(data: T?): DataState<T> {
            return DataState(State.LOADING, data)
        }

        fun <T> error(error: String): DataState<T> {
            return DataState(State.ERROR, null, error)
        }

        fun <T> empty(): DataState<T> {
            return DataState(State.EMPTY, null, null)
        }
    }
}
