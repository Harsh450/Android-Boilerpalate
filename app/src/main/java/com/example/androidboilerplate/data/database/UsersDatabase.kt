package com.example.androidboilerplate.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidboilerplate.data.database.dao.UsersDao
import com.example.androidboilerplate.screens.home.model.database.UserItem

@Database(entities = [UserItem::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
}