package com.rbs.pokemonapps.domain.repository

import androidx.paging.PagingData
import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.domain.model.PokeDomain
import com.rbs.pokemonapps.domain.model.PokeItemDomain
import kotlinx.coroutines.flow.Flow

interface PokeRepository {
    fun getListPoke(): Flow<PagingData<PokeItemDomain>>
    suspend fun getAllPokemon(query: String): ResultState<PokeDomain>
}