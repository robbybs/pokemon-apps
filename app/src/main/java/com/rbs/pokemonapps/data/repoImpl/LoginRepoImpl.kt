package com.rbs.pokemonapps.data.repoImpl

import com.rbs.pokemonapps.data.local.source.LoginLocalSource
import com.rbs.pokemonapps.domain.repository.LoginRepository

class LoginRepoImpl(
    private val dataSource: LoginLocalSource
) : LoginRepository {
    override suspend fun login(username: String, password: String): Boolean =
        dataSource.login(username, password)
}