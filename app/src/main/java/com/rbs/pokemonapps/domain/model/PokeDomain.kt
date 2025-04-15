package com.rbs.pokemonapps.domain.model

data class PokeDomain(
    val listPoke: List<PokeItemDomain>
)

data class PokeItemDomain(
    val name: String,
    val url: String,
    val id: Int
)
