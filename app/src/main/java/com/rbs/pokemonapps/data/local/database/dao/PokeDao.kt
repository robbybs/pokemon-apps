package com.rbs.pokemonapps.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.data.local.database.entity.UserEntity

@Dao
interface PokeDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT COUNT(*) FROM UserEntity WHERE username = :username AND password = :password")
    suspend fun getUserByUsername(username: String, password: String): Int

    @Query("SELECT * FROM UserEntity WHERE username = :username")
    suspend fun getDetailUser(username: String): UserEntity
}