package com.sanket.androidtestingrepo

import android.os.Handler
import android.os.Looper
import kotlin.random.Random

class MockAuthRepository: AuthRepository {
    override fun login(userName: String, password: String, responseCallback: ResponseCallback<SimpleResource>) {
        val resource = if (Random.nextInt(10) > 7) Resource.Error(UiText.unknownError())
        else Resource.Success(Unit)
        Handler(Looper.getMainLooper()).postDelayed({
            responseCallback.onResponse(resource)
        }, Random.nextLong(500, 2000))
    }
}