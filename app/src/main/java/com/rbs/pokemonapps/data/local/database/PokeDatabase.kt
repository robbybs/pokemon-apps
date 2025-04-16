package com.rbs.pokemonapps.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rbs.pokemonapps.data.local.database.dao.PokeDao
import com.rbs.pokemonapps.data.local.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
abstract class PokeDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokeDao
}