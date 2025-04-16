package com.rbs.pokemonapps.domain.usecase

import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.domain.model.UserDomain
import com.rbs.pokemonapps.domain.repository.ProfileRepository

class ProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend fun getProfile(name: String): ResultState<UserDomain> = repository.getProfile(name)
}