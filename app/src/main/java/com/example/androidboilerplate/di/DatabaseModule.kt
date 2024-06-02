package com.example.androidboilerplate.di

import android.content.Context
import androidx.room.Room
import com.example.androidboilerplate.data.database.dao.UsersDao
import com.example.androidboilerplate.data.database.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun appDatabaseProvider(@ApplicationContext appContext: Context): UsersDatabase {
        return Room.databaseBuilder(
            appContext,
            UsersDatabase::class.java,
            "Users.db"
        ).build()
    }

    @Provides
    fun userDaoProvider(usersDatabase: UsersDatabase): UsersDao {
        return usersDatabase.usersDao
    }

}