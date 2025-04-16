package com.rbs.pokemonapps.utils

import com.rbs.pokemonapps.data.local.database.entity.UserEntity
import com.rbs.pokemonapps.data.network.model.AbilityDetail
import com.rbs.pokemonapps.data.network.model.AbilityDetailData
import com.rbs.pokemonapps.data.network.model.AbilityItem
import com.rbs.pokemonapps.data.network.model.AbilityItemData
import com.rbs.pokemonapps.data.network.model.PokeData
import com.rbs.pokemonapps.data.network.model.PokeDetailData
import com.rbs.pokemonapps.data.network.model.PokeDetailResponse
import com.rbs.pokemonapps.data.network.model.PokeItemData
import com.rbs.pokemonapps.data.network.model.PokeItemResponse
import com.rbs.pokemonapps.data.network.model.PokeResponse
import com.rbs.pokemonapps.data.network.model.StatsDetail
import com.rbs.pokemonapps.data.network.model.StatsDetailData
import com.rbs.pokemonapps.data.network.model.StatsItem
import com.rbs.pokemonapps.data.network.model.StatsItemData
import com.rbs.pokemonapps.domain.model.PokeDetailDomain
import com.rbs.pokemonapps.domain.model.PokeDomain
import com.rbs.pokemonapps.domain.model.PokeItemDomain
import com.rbs.pokemonapps.domain.model.StatsItemDomain
import com.rbs.pokemonapps.domain.model.UserDomain

fun PokeResponse.toData(query: String) = PokeData(
    listPoke = listPoke.orEmpty().map { it.toData() }
        .filter { it.name.contains(query, ignoreCase = true) }
)

fun PokeItemResponse.toData() = PokeItemData(
    name = name.orEmpty(),
    url = url.orEmpty(),
    id = getPokeId(url.orEmpty())
)

fun getPokeId(url: String): Int = url.split("/").dropLast(1).lastOrNull()?.toIntOrNull() ?: -1

fun PokeData.toDomain() = PokeDomain(
    listPoke = listPoke.map { it.toDomain() }
)

fun PokeItemData.toDomain() = PokeItemDomain(
    name = name,
    url = url,
    id = id
)

fun PokeDetailResponse.toData() = PokeDetailData(
    listAbility = listAbility.orEmpty().map { it.toData() },
    experience = experience ?: 0,
    name = name.orEmpty(),
    height = height ?: 0,
    weight = weight ?: 0,
    listStats = listStats.orEmpty().map { it.toData() }
)

fun AbilityItem.toData() = AbilityItemData(
    ability = ability?.toData() ?: AbilityDetailData("")
)

fun AbilityDetail.toData() = AbilityDetailData(
    name = name.orEmpty()
)

fun StatsItem.toData() = StatsItemData(
    stats = stats ?: 0,
    statsDetail = statsDetail?.toData() ?: StatsDetailData("")
)

fun StatsDetail.toData() = StatsDetailData(
    name = name.orEmpty()
)

fun PokeDetailData.toDomain() = PokeDetailDomain(
    listAbility = listAbility.map { it.ability.name },
    experience = experience,
    name = name,
    height = height,
    weight = weight,
    listStats = listStats.map { it.toDomain() }
)

fun StatsItemData.toDomain() = StatsItemDomain(
    stats = stats,
    statsName = statsDetail.name
)

fun UserEntity.toDomain() = UserDomain(
    username = username,
    name = name,
    password = password
)