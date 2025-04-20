package com.rbs.pokemonapps.data.network.source

import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.data.network.service.ApiService
import com.rbs.pokemonapps.data.network.model.PokeDetailData
import com.rbs.pokemonapps.utils.toData

class DetailRemoteSource(
    private val apiService: ApiService
) {
    suspend fun getDetail(id: Int): ResultState<PokeDetailData> = try {
        val result = apiService.getDetailPokemon(id)
        ResultState.Success(result.toData())
    } catch (e: Exception) {
        ResultState.Error(e.message.toString())
    }
}