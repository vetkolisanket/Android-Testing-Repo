package com.sanket.androidtestingrepo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

inline fun <reified T : Activity> Context.openActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}