package com.rbs.pokemonapps.di

import com.rbs.pokemonapps.data.local.database.dao.PokeDao
import com.rbs.pokemonapps.data.repoImpl.LoginRepoImpl
import com.rbs.pokemonapps.data.local.source.LoginLocalSource
import com.rbs.pokemonapps.domain.repository.LoginRepository
import com.rbs.pokemonapps.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {
    @Provides
    @Singleton
    fun provideLoginLocalSource(dao: PokeDao): LoginLocalSource = LoginLocalSource(dao)

    @Provides
    @Singleton
    fun provideLoginRepository(dataSource: LoginLocalSource): LoginRepository = LoginRepoImpl(dataSource)

    @Provides
    @Singleton
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase = LoginUseCase(loginRepository)
}