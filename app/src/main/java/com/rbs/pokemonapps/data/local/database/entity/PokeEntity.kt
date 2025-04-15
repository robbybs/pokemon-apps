package com.rbs.pokemonapps.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokeEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String
)