package com.rbs.pokemonapps.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val username: String,
    val name: String,
    val password: String
)