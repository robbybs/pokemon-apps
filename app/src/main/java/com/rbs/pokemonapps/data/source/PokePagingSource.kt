package com.rbs.pokemonapps.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rbs.pokemonapps.data.network.ApiService
import com.rbs.pokemonapps.domain.model.PokeItemDomain
import com.rbs.pokemonapps.utils.toData
import com.rbs.pokemonapps.utils.toDomain

class PokePagingSource(
    private val apiService: ApiService
) : PagingSource<Int, PokeItemDomain>() {

    companion object {
        const val INITIAL_OFFSET = 0
    }

    override fun getRefreshKey(state: PagingState<Int, PokeItemDomain>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokeItemDomain> {
        val offset = params.key ?: INITIAL_OFFSET
        val limit = params.loadSize

        return try {
            val response = apiService.getPokemonList(offset, params.loadSize)
            val pokemonList = response.listPoke.orEmpty().map { it.toData() }
            val poke = pokemonList.map { it.toDomain() }

            val prevKey = if (offset == INITIAL_OFFSET) null else offset - limit
            val nextKey = if (poke.isEmpty()) null else offset + limit

            LoadResult.Page(
                data = poke,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}