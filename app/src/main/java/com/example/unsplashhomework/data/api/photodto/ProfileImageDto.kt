package com.example.unsplashhomework.data.api.photodto

import com.example.unsplashhomework.domain.model.ProfileImage

data class ProfileImageDto(
    val large: String,
    val medium: String,
    val small: String
) {
    fun toPhotoDetailsProfileImage() = ProfileImage(small)
}