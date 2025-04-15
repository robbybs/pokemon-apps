package com.rbs.pokemonapps.data.repoImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rbs.pokemonapps.data.source.PokePagingSource
import com.rbs.pokemonapps.domain.model.PokeItemDomain
import com.rbs.pokemonapps.domain.repository.PokeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class PokeRepoImpl(
    private val dataSource: PokePagingSource,
) : PokeRepository {
    override fun getListPoke(): Flow<PagingData<PokeItemDomain>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { dataSource }
    ).flow.flowOn(Dispatchers.IO)
}