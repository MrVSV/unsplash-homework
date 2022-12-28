package com.example.unsplashhomework.data.api.photodto

import com.example.unsplashhomework.data.model.Location

data class LocationDto(
    val city: String?,
    val country: String?,
    val position: PositionDto
) {
    fun toPhotoDetailsLocation() = Location(city, position.toPhotoDetailsPosition())
}