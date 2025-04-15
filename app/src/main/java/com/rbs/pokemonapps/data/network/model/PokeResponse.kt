package com.rbs.pokemonapps.data.network.model

import com.google.gson.annotations.SerializedName

data class PokeResponse(
    @SerializedName("results")
    val listPoke: List<PokeItemResponse>? = listOf()
)

data class PokeItemResponse(
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("url")
    val url: String? = ""
)