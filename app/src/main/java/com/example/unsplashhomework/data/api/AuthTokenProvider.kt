package com.example.unsplashhomework.data.api

import android.content.Context

class AuthTokenProvider(private val context: Context) {

    fun getToken(): String? {
        val pref = context.getSharedPreferences(TOKEN_SHARED_NAME, Context.MODE_PRIVATE)

        return pref.getString(TOKEN_SHARED_KEY, "")
    }

}