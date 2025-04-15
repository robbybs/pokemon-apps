package com.rbs.pokemonapps.utils

import com.rbs.pokemonapps.data.network.model.PokeData
import com.rbs.pokemonapps.data.network.model.PokeItemData
import com.rbs.pokemonapps.data.network.model.PokeItemResponse
import com.rbs.pokemonapps.data.network.model.PokeResponse
import com.rbs.pokemonapps.domain.model.PokeItemDomain

fun PokeResponse.toData() = PokeData(
    listPoke = listPoke.orEmpty().map { it.toData() }
)

fun PokeItemResponse.toData() = PokeItemData(
    name = name.orEmpty(),
    url = url.orEmpty(),
    id = getPokeId(url.orEmpty())
)

fun getPokeId(url: String): Int = url.split("/").dropLast(1).lastOrNull()?.toIntOrNull() ?: -1

fun PokeItemData.toDomain() = PokeItemDomain(
    name = name,
    url = url,
    id = id
)