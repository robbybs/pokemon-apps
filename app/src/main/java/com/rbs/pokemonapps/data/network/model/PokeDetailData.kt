package com.rbs.pokemonapps.data.network.model

data class PokeDetailData(
    val listAbility: List<AbilityItemData>,
    val experience: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val listStats: List<StatsItemData>
)

data class AbilityItemData(
    val ability: AbilityDetailData
)

data class AbilityDetailData(
    val name: String
)

data class StatsItemData(
    val stats: Int,
    val statsDetail: StatsDetailData
)

data class StatsDetailData(
    val name: String
)