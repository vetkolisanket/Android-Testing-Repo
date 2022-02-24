package com.sanket.androidtestingrepo

import androidx.lifecycle.LiveData

interface ILoginViewModel {
    fun getLoginLD(): LiveData<SimpleResource>

    fun getUsernameLD(): LiveData<SimpleResource>

    fun getPasswordLD(): LiveData<SimpleResource>

    fun login(username: String, password: String)
    fun getUsernameValidationErrorMessage(username: String): UiText?
    fun getPasswordValidationErrorMessage(password: String): UiText?
}