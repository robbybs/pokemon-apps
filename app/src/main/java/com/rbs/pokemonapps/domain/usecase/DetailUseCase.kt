package com.rbs.pokemonapps.domain.usecase

import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.domain.model.PokeDetailDomain
import com.rbs.pokemonapps.domain.repository.PokeDetailRepository

class DetailUseCase(
    private val repository: PokeDetailRepository
) {
    suspend fun getDetail(id: Int): ResultState<PokeDetailDomain> = repository.getDetailPokemon(id)
}