package com.example.androidboilerplate.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class PrefManager
@Inject constructor(
    val dataStore: DataStore<Preferences>
) {
    fun <T> getValue(key: String, defaultValue: T): Flow<T> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map {
                val value = when (defaultValue) {
                    is String -> {
                        it[stringPreferencesKey(key)]
                    }

                    is Boolean -> {
                        it[booleanPreferencesKey(key)]
                    }

                    is Int -> {
                        it[intPreferencesKey(key)]
                    }

                    is Float -> {
                        it[floatPreferencesKey(key)]
                    }

                    is Long -> {
                        it[longPreferencesKey(key)]
                    }

                    is Double -> {
                        it[doublePreferencesKey(key)]
                    }

                    else -> {
                        it[stringPreferencesKey(key)]
                    }
                }
                if (value == null) {
                    defaultValue
                } else {
                    value as T
                }
            }
    }

    suspend fun <T> setValue(key: String, value: T) {
        try {
            dataStore.edit {
                when (value) {
                    is String -> {
                        it[stringPreferencesKey(key)] = value
                    }

                    is Boolean -> {
                        it[booleanPreferencesKey(key)] = value
                    }

                    is Int -> {
                        it[intPreferencesKey(key)] = value
                    }

                    is Float -> {
                        it[floatPreferencesKey(key)] = value
                    }

                    is Long -> {
                        it[longPreferencesKey(key)] = value
                    }

                    is Double -> {
                        it[doublePreferencesKey(key)] = value
                    }

                    else -> {}
                }
            }
        } catch (e: IOException) {
            Logger.e("setValue: $e")
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> getObject(
        key: Preferences.Key<String>,
        defaultValue: T
    ): Flow<T> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val json = preferences[key] ?: return@map defaultValue
                Json.decodeFromString<T>(json)
            }
    }


    @OptIn(ExperimentalSerializationApi::class)
    suspend inline fun <reified T : Any> setObject(
        key: Preferences.Key<String>,
        value: T
    ) {
        try {
            val json = Json.encodeToString(value)
            dataStore.edit { preferences ->
                preferences[key] = json
            }
        } catch (e: IOException) {
            Logger.e("setObject: $e")
        }
    }
}