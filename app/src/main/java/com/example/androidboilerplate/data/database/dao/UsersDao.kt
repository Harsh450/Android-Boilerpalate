package com.example.androidboilerplate.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidboilerplate.screens.home.model.database.UserItem

@Dao
interface UsersDao {

    @Query("select * from user")
    fun getDatabaseUsers(): LiveData<List<UserItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserItem>)
}