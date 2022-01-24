package com.sanket.androidtestingrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val loginLD = MutableLiveData<SimpleResource>()
    private val usernameLD = MutableLiveData<SimpleResource>()
    private val passwordLD = MutableLiveData<SimpleResource>()

    open fun getLoginLD(): LiveData<SimpleResource> = loginLD
    open fun getUsernameLD(): LiveData<SimpleResource> = usernameLD
    open fun getPasswordLD(): LiveData<SimpleResource> = passwordLD

    open fun login(username: String, password: String) {
        loginLD.value = Resource.Loading

        val usernameError = if (username.length < 4) AuthError.InputTooShort else null
        val passwordError = if (password.length < 6) AuthError.InputTooShort else null

        if (usernameError != null || passwordError != null) {
            if (usernameError != null) {
                usernameLD.value = Resource.Error(UiText.StringResource(R.string.username_short_error))
                return
            }

            if (passwordError != null) {
                passwordLD.value = Resource.Error(UiText.StringResource(R.string.password_short_error))
                return
            }
        }

        repository.login(username, password, object : ResponseCallback<SimpleResource> {
            override fun onResponse(response: SimpleResource) {
                loginLD.value = response
            }
        })
    }

    fun getUsernameValidationErrorMessage(username: String): UiText? {
        if (username.length < 4) return UiText.StringResource(R.string.username_short_error)
        return null
    }

    fun getPasswordValidationErrorMessage(password: String): UiText? {
        if (password.length < 6) return UiText.StringResource(R.string.password_short_error)
        if (!password.matches(".*[A-Z].*".toRegex())) return UiText.StringResource(R.string.password_no_uppercase_error)
        if (!password.matches(".*[a-z].*".toRegex())) return UiText.StringResource(R.string.password_no_lowercase_error)
        if (!password.matches(".*[0-9].*".toRegex())) return UiText.StringResource(R.string.password_no_digit_error)
        if (!password.matches(".*[!@#\$%^&*].*".toRegex())) return UiText.StringResource(R.string.password_no_uppercase_error)
        return null
    }

}