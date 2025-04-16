package com.rbs.pokemonapps.data.network

import com.rbs.pokemonapps.data.network.model.PokeDetailResponse
import com.rbs.pokemonapps.data.network.model.PokeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getAllPokemon(
        @Query("limit") limit: Int = 1302
    ): PokeResponse

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokeResponse

    @GET("pokemon/{id}")
    suspend fun getDetailPokemon(
        @Path("id") id: Int
    ): PokeDetailResponse
}