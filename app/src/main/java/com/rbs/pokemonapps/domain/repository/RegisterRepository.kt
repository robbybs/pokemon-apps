package com.rbs.pokemonapps.domain.repository

interface RegisterRepository {
    fun registerUser(username: String, email: String, password: String)
}