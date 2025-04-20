package com.rbs.pokemonapps.data.network.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rbs.pokemonapps.data.network.service.ApiService
import com.rbs.pokemonapps.data.network.model.PokeItemData
import com.rbs.pokemonapps.utils.toData

class PokePagingSource(
    private val apiService: ApiService
) : PagingSource<Int, PokeItemData>() {

    companion object {
        const val INITIAL_OFFSET = 0
    }

    override fun getRefreshKey(state: PagingState<Int, PokeItemData>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokeItemData> {
        val offset = params.key ?: INITIAL_OFFSET
        val limit = params.loadSize

        return try {
            val response = apiService.getPokemonList(offset, params.loadSize)
            val pokemonList = response.listPoke.orEmpty().map { it.toData() }

            val prevKey = if (offset == INITIAL_OFFSET) null else offset - limit
            val nextKey = if (pokemonList.isEmpty()) null else offset + limit

            LoadResult.Page(
                data = pokemonList,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}