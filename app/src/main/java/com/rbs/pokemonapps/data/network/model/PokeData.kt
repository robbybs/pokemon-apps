package com.rbs.pokemonapps.data.network.model

data class PokeData(
    val listPoke: List<PokeItemData>
)

data class PokeItemData(
    val name: String,
    val url: String,
    val id: Int
)