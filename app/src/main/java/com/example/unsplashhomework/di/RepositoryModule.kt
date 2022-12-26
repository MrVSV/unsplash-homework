package com.example.unsplashhomework.di

import com.example.unsplashhomework.data.repository.LocalRepositoryImpl
import com.example.unsplashhomework.data.repository.RemotePhotoRepositoryImpl
import com.example.unsplashhomework.domain.LocalRepository
import com.example.unsplashhomework.domain.RemotePhotoRepository
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
        remoteRepository: RemotePhotoRepositoryImpl
    ): RemotePhotoRepository

    @Singleton
    @Binds
    abstract fun bindLocalRepository(
        localRepository: LocalRepositoryImpl
    ): LocalRepository
}