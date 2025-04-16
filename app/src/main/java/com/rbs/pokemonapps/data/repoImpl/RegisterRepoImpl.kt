package com.rbs.pokemonapps.data.repoImpl

import com.rbs.pokemonapps.data.source.RegisterLocalSource
import com.rbs.pokemonapps.domain.repository.RegisterRepository

class RegisterRepoImpl(
    private val dataSource: RegisterLocalSource
) : RegisterRepository {
    override suspend fun registerUser(name: String, username: String, password: String): Boolean =
        dataSource.insertUser(name, username, password)
}