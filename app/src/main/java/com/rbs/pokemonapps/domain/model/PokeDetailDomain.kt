package com.rbs.pokemonapps.domain.model

data class PokeDetailDomain(
    val listAbility: List<String>,
    val experience: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val listStats: List<StatsItemDomain>
)

data class StatsItemDomain(
    val stats: Int,
    val statsName: String
)
