package com.rbs.pokemonapps.data.repoImpl

import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.data.source.ProfileLocalSource
import com.rbs.pokemonapps.domain.model.UserDomain
import com.rbs.pokemonapps.domain.repository.ProfileRepository

class ProfileRepoImpl(
    private val dataSource: ProfileLocalSource
) : ProfileRepository {
    override suspend fun getProfile(username: String): ResultState<UserDomain> = dataSource.getProfile(username)
}