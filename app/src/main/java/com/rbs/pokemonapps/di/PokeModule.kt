package com.rbs.pokemonapps.di

import com.rbs.pokemonapps.data.network.ApiService
import com.rbs.pokemonapps.data.source.PokePagingSource
import com.rbs.pokemonapps.data.repoImpl.PokeRepoImpl
import com.rbs.pokemonapps.domain.repository.PokeRepository
import com.rbs.pokemonapps.domain.usecase.PokeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokeModule {
    @Provides
    @Singleton
    fun providePokePagingSource(apiService: ApiService): PokePagingSource =
        PokePagingSource(apiService)

    @Provides
    @Singleton
    fun providePokeRepository(
        dataSource: PokePagingSource
    ): PokeRepository = PokeRepoImpl(dataSource)

    @Provides
    fun providePokeUseCase(pokeRepository: PokeRepository): PokeUseCase =
        PokeUseCase(pokeRepository)
}