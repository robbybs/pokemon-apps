package com.rbs.pokemonapps.domain.repository

interface LoginRepository {
    suspend fun login(username: String, password: String): Boolean
}