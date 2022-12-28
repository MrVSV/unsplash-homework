package com.example.unsplashhomework.data.api.photodto

import com.example.unsplashhomework.data.model.Tags

data class TagDto(
    val title: String?
) {
    fun toPhotoDetailsTags() = Tags(title)
}