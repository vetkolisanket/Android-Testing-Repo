package com.sanket.androidtestingrepo

interface ResponseCallback<T> {
    fun onResponse(response: T)
}