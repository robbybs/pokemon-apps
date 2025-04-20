package com.rbs.pokemonapps.data.network.source

import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.data.network.service.ApiService
import com.rbs.pokemonapps.data.network.model.PokeData
import com.rbs.pokemonapps.utils.toData

class PokeRemoteSource(
    private val apiService: ApiService
) {
    suspend fun getAllData(query: String): ResultState<PokeData> = try {
        val result = apiService.getAllPokemon()
        ResultState.Success(result.toData(query))
    } catch (e: Exception) {
        ResultState.Error(e.message.toString())
    }
}