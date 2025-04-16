package com.rbs.pokemonapps.domain.usecase

import com.rbs.pokemonapps.domain.repository.RegisterRepository

class RegisterUseCase(
    private val repository: RegisterRepository
) {
    suspend fun registerUser(name: String, username: String, password: String) : Boolean = repository.registerUser(name, username, password)
}