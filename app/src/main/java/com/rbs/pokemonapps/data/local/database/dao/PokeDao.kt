package com.rbs.pokemonapps.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rbs.pokemonapps.data.local.database.entity.PokeEntity

@Dao
interface PokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<PokeEntity>)

    @Query("SELECT * FROM PokeEntity")
    suspend fun getAllPokemon(): List<PokeEntity>

    @Query("SELECT * FROM PokeEntity WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokeEntity
}