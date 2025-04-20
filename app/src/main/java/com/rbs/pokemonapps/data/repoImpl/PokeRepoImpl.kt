package com.rbs.pokemonapps.data.repoImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.data.network.source.PokePagingSource
import com.rbs.pokemonapps.data.network.source.PokeRemoteSource
import com.rbs.pokemonapps.domain.model.PokeDomain
import com.rbs.pokemonapps.domain.model.PokeItemDomain
import com.rbs.pokemonapps.domain.repository.PokeRepository
import com.rbs.pokemonapps.utils.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PokeRepoImpl(
    private val dataSource: PokePagingSource,
    private val allDataSource: PokeRemoteSource
) : PokeRepository {
    override fun getListPoke(): Flow<PagingData<PokeItemDomain>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { dataSource }
    ).flow
        .map { it.map { data -> data.toDomain() } }
        .flowOn(Dispatchers.IO)

    override suspend fun getAllPokemon(query: String): ResultState<PokeDomain> =
        when (val result = allDataSource.getAllData(query)) {
            is ResultState.Success -> ResultState.Success(result.data.toDomain())
            is ResultState.Error -> ResultState.Error(result.message)
            else -> ResultState.Error("Unknown error")
        }
}