package com.rbs.pokemonapps.data.repoImpl

import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.data.source.DetailRemoteSource
import com.rbs.pokemonapps.domain.model.PokeDetailDomain
import com.rbs.pokemonapps.domain.repository.PokeDetailRepository
import com.rbs.pokemonapps.utils.toDomain

class PokeDetailRepoImpl(
    val dataSource: DetailRemoteSource,
) : PokeDetailRepository {
    override suspend fun getDetailPokemon(id: Int): ResultState<PokeDetailDomain> =
        when (val result = dataSource.getDetail(id)) {
            is ResultState.Success -> ResultState.Success(result.data.toDomain())
            is ResultState.Error -> ResultState.Error(result.message)
            else -> ResultState.Error("Unknown error")
        }
}