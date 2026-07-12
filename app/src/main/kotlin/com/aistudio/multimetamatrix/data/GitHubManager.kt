package com.aistudio.multimetamatrix.data

import android.content.Context
import android.content.SharedPreferences

object GitHubManager {
    private const val PREFS_NAME = "github_prefs"
    private const val KEY_TOKEN = "github_token"
    private const val KEY_CONNECTED = "github_connected"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setToken(context: Context, token: String) {
        getPrefs(context).edit().putString(KEY_TOKEN, token).apply()
        setConnected(context, token.isNotEmpty())
    }

    fun getToken(context: Context): String? {
        return getPrefs(context).getString(KEY_TOKEN, null)
    }

    fun setConnected(context: Context, connected: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_CONNECTED, connected).apply()
    }

    fun isConnected(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_CONNECTED, false)
    }
}
