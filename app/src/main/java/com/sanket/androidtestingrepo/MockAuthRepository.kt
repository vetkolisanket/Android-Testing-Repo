package com.sanket.androidtestingrepo

import android.os.Handler
import android.os.Looper
import kotlin.random.Random

class MockAuthRepository: AuthRepository {
    override fun login(userName: String, password: String): SimpleResource {
        return if (Random.nextInt(10) > 7) Resource.Error(UiText.unknownError())
        else Resource.Success(Unit)
    }
}