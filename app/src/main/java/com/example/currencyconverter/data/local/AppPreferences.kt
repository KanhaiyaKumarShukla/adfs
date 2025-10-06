package com.example.currencyconverter.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@Singleton
class AppPreferences @Inject constructor(
    private val context: Context
) {
    private val dataStore = context.dataStore
    
    private object PreferencesKeys {
        val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
    }
    
    val isFirstLaunchFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_FIRST_LAUNCH] ?: true
        }
    
    suspend fun isFirstLaunch(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.IS_FIRST_LAUNCH] ?: true
        }.first()
    }
    
    suspend fun setFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }
    
    private suspend inline fun <T> DataStore<Preferences>.edit(
        crossinline action: (MutablePreferences) -> Unit
    ) {
        edit { preferences ->
            action(preferences)
        }
    }
    
    private suspend inline fun <T> Flow<T>.first(): T = first()
}
