package com.rbs.pokemonapps.domain.repository

import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.domain.model.PokeDetailDomain

interface PokeDetailRepository {
    suspend fun getDetailPokemon(id: Int): ResultState<PokeDetailDomain>
}