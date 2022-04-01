package com.sanket.androidtestingrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel(), ILoginViewModel {

    private val loginLD = MutableLiveData<SimpleResource>()

    override fun getLoginLD(): LiveData<SimpleResource> = loginLD

    override fun login(username: String, password: String) {
        loginLD.value = Resource.Loading

        if (username.length < 4) {
            loginLD.value = Resource.Error(UiText.StringResource(R.string.username_short_error))
            return
        }

        if (password.length < 6) {
            loginLD.value = Resource.Error(UiText.StringResource(R.string.password_short_error))
            return
        }

        repository.login(username, password, object : ResponseCallback<SimpleResource> {
            override fun onResponse(response: SimpleResource) {
                loginLD.value = response
            }
        })
    }

    override fun getUsernameValidationErrorMessage(username: String): UiText? {
        if (username.length < 4) return UiText.StringResource(R.string.username_short_error)
        return null
    }

    override fun getPasswordValidationErrorMessage(password: String): UiText? {
        if (password.length < 6) return UiText.StringResource(R.string.password_short_error)
        if (!password.matches(".*[A-Z].*".toRegex())) return UiText.StringResource(R.string.password_no_uppercase_error)
        if (!password.matches(".*[a-z].*".toRegex())) return UiText.StringResource(R.string.password_no_lowercase_error)
        if (!password.matches(".*[0-9].*".toRegex())) return UiText.StringResource(R.string.password_no_digit_error)
        if (!password.matches(".*[!@#\$%^&*].*".toRegex())) return UiText.StringResource(R.string.password_no_uppercase_error)
        return null
    }

}