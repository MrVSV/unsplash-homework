package com.example.unsplashhomework.presentation.user

import com.example.unsplashhomework.domain.model.Profile

sealed class ProfileState{
    data class Success(val data: Profile): ProfileState()
    object NotStartedYet : ProfileState()
}
