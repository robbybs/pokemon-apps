package com.rbs.pokemonapps.data.repoImpl

import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.data.local.source.ProfileLocalSource
import com.rbs.pokemonapps.domain.model.UserDomain
import com.rbs.pokemonapps.domain.repository.ProfileRepository
import com.rbs.pokemonapps.utils.toDomain

class ProfileRepoImpl(
    private val dataSource: ProfileLocalSource
) : ProfileRepository {
    override suspend fun getProfile(username: String): ResultState<UserDomain> =
        when (val result = dataSource.getProfile(username)) {
            is ResultState.Success -> ResultState.Success(result.data.toDomain())
            is ResultState.Error -> ResultState.Error(result.message)
            else -> ResultState.Error("Unknown error")
        }
}