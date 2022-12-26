package com.example.unsplashhomework.di

import com.example.unsplashhomework.data.repository.DigestRemoteRepositoryImpl
import com.example.unsplashhomework.data.repository.LocalRepositoryImpl
import com.example.unsplashhomework.data.repository.PhotoRemoteRepositoryImpl
import com.example.unsplashhomework.domain.DigestRemoteRepository
import com.example.unsplashhomework.domain.LocalRepository
import com.example.unsplashhomework.domain.PhotoRemoteRepository
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
    abstract fun bindPhotoRemoteRepository(
        photoRemoteRepository: PhotoRemoteRepositoryImpl
    ): PhotoRemoteRepository

    @Singleton
    @Binds
    abstract fun bindLocalRepository(
        localRepository: LocalRepositoryImpl
    ): LocalRepository

    @Singleton
    @Binds
    abstract fun bindDigestRemoteRepository(
        digestRemoteRepository: DigestRemoteRepositoryImpl
    ): DigestRemoteRepository
}