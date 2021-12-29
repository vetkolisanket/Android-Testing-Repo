package com.sanket.androidtestingrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val loginLD = MutableLiveData<SimpleResource>()
    private val usernameLD = MutableLiveData<SimpleResource>()
    private val passwordLD = MutableLiveData<SimpleResource>()

    fun getLoginLD(): LiveData<SimpleResource> = loginLD
    fun getUsernameLD(): LiveData<SimpleResource> = usernameLD
    fun getPasswordLD(): LiveData<SimpleResource> = passwordLD

    fun login(username: String, password: String) {
        loginLD.value = Resource.Loading

        val usernameError = if (username.length < 4) AuthError.InputTooShort else null
        val passwordError = if (password.length < 6) AuthError.InputTooShort else null

        if (usernameError != null || passwordError != null) {
            if (usernameError != null) {
                usernameLD.value = Resource.Error(UiText.StringResource(R.string.username_error))
                return
            }

            if (passwordError != null) {
                passwordLD.value = Resource.Error(UiText.StringResource(R.string.password_error))
                return
            }
        }

        repository.login(username, password, object : ResponseCallback<SimpleResource> {
            override fun onResponse(response: SimpleResource) {
                loginLD.value = response
            }
        })
    }


}