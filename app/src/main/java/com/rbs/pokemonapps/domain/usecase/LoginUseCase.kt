package com.rbs.pokemonapps.domain.usecase

import com.rbs.pokemonapps.domain.repository.LoginRepository

class LoginUseCase(
    private val repository: LoginRepository
) {
    suspend fun login(username: String, password: String): Boolean = repository.login(username, password)
}