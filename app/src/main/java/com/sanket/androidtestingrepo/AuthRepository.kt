package com.sanket.androidtestingrepo

interface AuthRepository {

    fun login(userName: String, password: String): SimpleResource

}