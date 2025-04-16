package com.rbs.pokemonapps.data.source

import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.data.local.database.dao.PokeDao
import com.rbs.pokemonapps.domain.model.UserDomain
import com.rbs.pokemonapps.utils.toDomain

class ProfileLocalSource(
    private val dao: PokeDao
) {
    suspend fun getProfile(username: String): ResultState<UserDomain> = try {
        val result = dao.getDetailUser(username).toDomain()
        ResultState.Success(result)
    } catch (e: Exception) {
        ResultState.Error(e.message.toString())
    }
}