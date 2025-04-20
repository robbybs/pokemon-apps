package com.rbs.pokemonapps.data.local.source

import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.data.local.database.dao.PokeDao
import com.rbs.pokemonapps.data.local.model.UserData
import com.rbs.pokemonapps.domain.model.UserDomain
import com.rbs.pokemonapps.utils.toData
import com.rbs.pokemonapps.utils.toDomain

class ProfileLocalSource(
    private val dao: PokeDao
) {
    suspend fun getProfile(username: String): ResultState<UserData> = try {
        val result = dao.getDetailUser(username).toData()
        ResultState.Success(result)
    } catch (e: Exception) {
        ResultState.Error(e.message.toString())
    }
}