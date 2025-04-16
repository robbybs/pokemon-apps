package com.rbs.pokemonapps.data.network.model

import com.google.gson.annotations.SerializedName

data class PokeDetailResponse(
    @SerializedName("abilities")
    val listAbility: List<AbilityItem>? = listOf(),
    @SerializedName("base_experience")
    val experience: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("height")
    val height: Int? = 0,
    @SerializedName("weight")
    val weight: Int? = 0,
    @SerializedName("stats")
    val listStats: List<StatsItem>? = listOf()
)

data class AbilityItem(
    @SerializedName("ability")
    val ability: AbilityDetail? = null
)

data class AbilityDetail(
    @SerializedName("name")
    val name: String? = "",
)

data class StatsItem(
    @SerializedName("base_stat")
    val stats: Int? = 0,
    @SerializedName("stat")
    val statsDetail: StatsDetail? = null
)

data class StatsDetail(
    @SerializedName("name")
    val name: String? = ""
)