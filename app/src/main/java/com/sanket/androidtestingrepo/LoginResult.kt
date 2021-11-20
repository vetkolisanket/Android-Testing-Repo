package com.sanket.androidtestingrepo

data class LoginResult(
    val userNameError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)