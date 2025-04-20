package com.rbs.pokemonapps.di

import com.rbs.pokemonapps.data.local.database.dao.PokeDao
import com.rbs.pokemonapps.data.repoImpl.ProfileRepoImpl
import com.rbs.pokemonapps.data.local.source.ProfileLocalSource
import com.rbs.pokemonapps.domain.repository.ProfileRepository
import com.rbs.pokemonapps.domain.usecase.ProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    @Singleton
    fun provideProfileLocalSource(dao: PokeDao): ProfileLocalSource = ProfileLocalSource(dao)

    @Provides
    @Singleton
    fun provideProfileRepository(dataSource: ProfileLocalSource): ProfileRepository = ProfileRepoImpl(dataSource)

    @Provides
    @Singleton
    fun provideProfileUseCase(repository: ProfileRepository): ProfileUseCase = ProfileUseCase(repository)
}