package com.rbs.pokemonapps.di

import com.rbs.pokemonapps.data.local.database.dao.PokeDao
import com.rbs.pokemonapps.data.repoImpl.RegisterRepoImpl
import com.rbs.pokemonapps.data.local.source.RegisterLocalSource
import com.rbs.pokemonapps.domain.repository.RegisterRepository
import com.rbs.pokemonapps.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {
    @Provides
    @Singleton
    fun provideRegisterLocalSource(dao: PokeDao): RegisterLocalSource = RegisterLocalSource(dao)

    @Provides
    @Singleton
    fun provideRegisterRepository(dataSource: RegisterLocalSource): RegisterRepository = RegisterRepoImpl(dataSource)

    @Provides
    @Singleton
    fun provideRegisterUseCase(registerRepository: RegisterRepository): RegisterUseCase = RegisterUseCase(registerRepository)
}