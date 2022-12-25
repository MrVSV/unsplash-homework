package com.example.unsplashhomework.data.repository

import androidx.paging.PagingSource
import com.example.unsplashhomework.data.local.PhotosDao
import com.example.unsplashhomework.data.local.entity.PhotoEntity
import com.example.unsplashhomework.domain.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(private val photosDao: PhotosDao): LocalRepository {

    override suspend fun insertData(data: List<PhotoEntity>) = photosDao.insert(data)

    override fun getPagingData(): PagingSource<Int, PhotoEntity> = photosDao.getPhotos()

    override suspend fun clear() = photosDao.deleteAll()

    override suspend fun setLikeInDataBase(photoEntity: PhotoEntity) = photosDao.updateLocalLikes(photoEntity)

    override suspend fun refresh(data: List<PhotoEntity>) = photosDao.refresh(data)
}