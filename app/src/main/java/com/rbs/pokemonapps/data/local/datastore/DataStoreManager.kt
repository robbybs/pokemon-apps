package com.rbs.pokemonapps.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val KEY_USER_ID = stringPreferencesKey("user_id")
    }

    suspend fun saveLoginState(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_IS_LOGGED_IN] = isLoggedIn
        }
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[KEY_IS_LOGGED_IN] ?: false
    }

    suspend fun clearLoginState() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_IS_LOGGED_IN)
        }
    }

    suspend fun saveUserId(userId: String) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = userId
        }
    }

    val getUserId: Flow<String?> = dataStore.data.map { preferences ->
        preferences[KEY_USER_ID]
    }
}