package com.rbs.pokemonapps.di

import com.rbs.pokemonapps.data.network.service.ApiService
import com.rbs.pokemonapps.data.repoImpl.PokeDetailRepoImpl
import com.rbs.pokemonapps.data.network.source.DetailRemoteSource
import com.rbs.pokemonapps.domain.repository.PokeDetailRepository
import com.rbs.pokemonapps.domain.usecase.DetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {
    @Provides
    @Singleton
    fun provideDetailRemoteSource(apiService: ApiService): DetailRemoteSource = DetailRemoteSource(apiService)

    @Provides
    @Singleton
    fun provideDetailRepository(remoteSource: DetailRemoteSource): PokeDetailRepository = PokeDetailRepoImpl(remoteSource)

    @Provides
    @Singleton
    fun provideDetailUseCase(repository: PokeDetailRepository): DetailUseCase = DetailUseCase(repository)
}