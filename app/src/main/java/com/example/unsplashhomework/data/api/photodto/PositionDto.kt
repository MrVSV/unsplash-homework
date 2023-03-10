package com.example.unsplashhomework.data.api.photodto

import com.example.unsplashhomework.domain.model.Position

data class PositionDto(
    val latitude: Double?,
    val longitude: Double?
) {
    fun toPhotoDetailsPosition() = Position(latitude, longitude)
}