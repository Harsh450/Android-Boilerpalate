package com.example.androidboilerplate.screens.home.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
data class UserItem (
    @PrimaryKey
    val id: Int,
    val userName: String,
    val password: String
)