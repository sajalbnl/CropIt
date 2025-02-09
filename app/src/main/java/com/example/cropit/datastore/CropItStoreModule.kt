package com.example.cropit.datastore

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CropItStoreModule(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("img_pref")
        private val HAS_ACCOUNT = booleanPreferencesKey("has_account")

    }

    val hasAccount: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[HAS_ACCOUNT] ?: false
    }

    suspend fun setHasAccount(hasAccount: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[HAS_ACCOUNT] = hasAccount
        }
    }

}