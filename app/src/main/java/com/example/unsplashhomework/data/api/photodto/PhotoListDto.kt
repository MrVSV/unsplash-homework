package com.example.unsplashhomework.data.api.photodto

import com.example.unsplashhomework.data.local.entity.PhotoEntity

class PhotoListDto : ArrayList<PhotoDto>(){

    fun toListEntity(): MutableList<PhotoEntity> {
        val newList = mutableListOf<PhotoEntity>()
        this.forEach {
            newList.add(it.toPhotoEntity())
        }
        return newList
    }
}