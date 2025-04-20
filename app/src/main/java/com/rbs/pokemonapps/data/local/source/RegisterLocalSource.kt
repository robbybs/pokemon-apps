package com.rbs.pokemonapps.data.local.source

import com.rbs.pokemonapps.data.local.database.dao.PokeDao
import com.rbs.pokemonapps.data.local.database.entity.UserEntity

class RegisterLocalSource(
    private val dao: PokeDao
) {
    suspend fun insertUser(name: String, username: String, password: String) : Boolean = try {
        val user = UserEntity(username, name, password)
        dao.insertUser(user)
        true
    } catch (e: Exception) { false }
}