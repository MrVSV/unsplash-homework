package com.example.unsplashhomework.data.repository

import androidx.paging.PagingSource
import com.example.unsplashhomework.data.local.PhotosDao
import com.example.unsplashhomework.data.local.entity.PhotoEntity
import com.example.unsplashhomework.domain.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(private val photosDao: PhotosDao): LocalRepository {

    override suspend fun insertData(data: List<PhotoEntity>) = photosDao.insert(data)

    override fun getPagingData(): PagingSource<Int, PhotoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }

    override suspend fun setLickInDataBase(photoEntity: PhotoEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(data: List<PhotoEntity>) {
        TODO("Not yet implemented")
    }
}