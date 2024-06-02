package com.example.androidboilerplate.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preference")

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}


