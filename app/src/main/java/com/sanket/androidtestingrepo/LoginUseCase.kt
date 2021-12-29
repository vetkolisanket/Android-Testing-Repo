package com.sanket.androidtestingrepo

class LoginUseCase(
    private val repository: AuthRepository
) {

    /*suspend operator fun invoke(userName: String, password: String): LoginResult {
        val trimUserName = userName.trim()
        val trimPassword = password.trim()

        val userNameError = if (trimUserName.length < 4) AuthError.InputTooShort else null
        val passwordError = if (trimPassword.length < 6) AuthError.InputTooShort else null

        if (userNameError != null || passwordError != null)
            LoginResult(userNameError, passwordError)

        return LoginResult(result = repository.login(trimUserName, trimPassword))
    }*/

}