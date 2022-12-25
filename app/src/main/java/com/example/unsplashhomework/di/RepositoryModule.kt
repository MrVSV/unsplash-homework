package com.example.unsplashhomework.di

import com.example.unsplashhomework.data.repository.LocalRepositoryImpl
import com.example.unsplashhomework.data.repository.RemoteRepositoryImpl
import com.example.unsplashhomework.domain.LocalRepository
import com.example.unsplashhomework.domain.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRemoteRepository(
        remoteRepository: RemoteRepositoryImpl
    ): RemoteRepository

    @Singleton
    @Binds
    abstract fun bindLocalRepository(
        localRepository: LocalRepositoryImpl
    ): LocalRepository
}