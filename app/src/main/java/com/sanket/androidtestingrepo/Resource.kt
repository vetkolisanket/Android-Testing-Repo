package com.sanket.androidtestingrepo

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T? = null, val uiText: UiText? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(uiText: UiText?, data: T? = null): Resource<T>(data, uiText)
    object Loading: Resource<Unit>()

    companion object {
        fun unknownError() = Error<Unit>(UiText.unknownError())
    }
}
