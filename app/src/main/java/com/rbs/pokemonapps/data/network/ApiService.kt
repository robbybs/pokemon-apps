package com.rbs.pokemonapps.data.network

import com.rbs.pokemonapps.data.network.model.PokeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokeResponse
}