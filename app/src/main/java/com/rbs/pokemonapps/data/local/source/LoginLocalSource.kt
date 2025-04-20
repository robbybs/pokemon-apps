package com.rbs.pokemonapps.data.local.source

import com.rbs.pokemonapps.data.local.database.dao.PokeDao

class LoginLocalSource(
    private val dao: PokeDao
) {
    suspend fun login(username: String, password: String): Boolean = try {
        val user = dao.getUserByUsername(username, password)
        user > 0
    } catch (e: Exception) {
        false
    }
}