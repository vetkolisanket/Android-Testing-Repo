package com.sanket.androidtestingrepo

sealed class AuthError: Error() {
    object InputTooShort: AuthError()
    object InvalidUserName: AuthError()
    object InvalidPassword: AuthError()
}
