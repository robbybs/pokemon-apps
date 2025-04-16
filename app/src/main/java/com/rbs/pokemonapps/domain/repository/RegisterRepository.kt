package com.rbs.pokemonapps.domain.repository

interface RegisterRepository {
    suspend fun registerUser(name: String, username: String, password: String) : Boolean
}