package com.aistudio.multimetamatrix.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "app_prefs")

class AppPreferences(private val context: Context) {
    companion object {
        val GITHUB_TOKEN = stringPreferencesKey("github_token")
        val GITHUB_CONNECTED = booleanPreferencesKey("github_connected")
        val AUTO_SYNC = booleanPreferencesKey("auto_sync")
    }

    val githubToken: Flow<String?> = context.dataStore.data.map { it[GITHUB_TOKEN] }
    val githubConnected: Flow<Boolean> = context.dataStore.data.map { it[GITHUB_CONNECTED] ?: false }
    val autoSync: Flow<Boolean> = context.dataStore.data.map { it[AUTO_SYNC] ?: false }

    suspend fun updateGithubToken(token: String) {
        context.dataStore.edit { it[GITHUB_TOKEN] = token }
    }

    suspend fun updateGithubConnected(connected: Boolean) {
        context.dataStore.edit { it[GITHUB_CONNECTED] = connected }
    }

    suspend fun updateAutoSync(enabled: Boolean) {
        context.dataStore.edit { it[AUTO_SYNC] = enabled }
    }
}
