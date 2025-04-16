package com.rbs.pokemonapps.domain.repository

import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.domain.model.UserDomain

interface ProfileRepository {
    suspend fun getProfile(username: String): ResultState<UserDomain>
}