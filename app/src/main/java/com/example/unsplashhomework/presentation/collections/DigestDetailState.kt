package com.example.unsplashhomework.presentation.collections

import com.example.unsplashhomework.domain.model.Digest

sealed class DigestState{
    data class Success(val data: Digest): DigestState()
    object NotStartedYet : DigestState()
}
