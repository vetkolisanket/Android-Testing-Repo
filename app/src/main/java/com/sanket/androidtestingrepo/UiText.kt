package com.sanket.androidtestingrepo

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicString(val value: String): UiText()
    data class StringResource(@StringRes val id: Int): UiText()

    fun getText(context: Context): String {
        return when (this) {
            is StringResource -> context.getString(this.id)
            is DynamicString -> this.value
        }
    }

    companion object {
        fun unknownError(): UiText {
            return StringResource(R.string.unknown_error)
        }
    }
}