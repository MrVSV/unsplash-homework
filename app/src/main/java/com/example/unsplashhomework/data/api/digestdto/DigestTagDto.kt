package com.example.unsplashhomework.data.api.digestdto

import com.example.unsplashhomework.domain.model.DigestTag

data class DigestTagDto(
    val title: String
) {

    fun toDigestTag() = DigestTag(title)
}
