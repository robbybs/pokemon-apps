package com.rbs.pokemonapps.domain.usecase

import androidx.paging.PagingData
import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.domain.model.PokeDomain
import com.rbs.pokemonapps.domain.model.PokeItemDomain
import com.rbs.pokemonapps.domain.repository.PokeRepository
import kotlinx.coroutines.flow.Flow

class PokeUseCase(
    private val repository: PokeRepository
) {
    fun getListPoke(): Flow<PagingData<PokeItemDomain>> = repository.getListPoke()
    suspend fun getAllPoke(query: String): ResultState<PokeDomain> = repository.getAllPokemon(query)
}