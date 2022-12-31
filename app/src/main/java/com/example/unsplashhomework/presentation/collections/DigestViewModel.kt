package com.example.unsplashhomework.presentation.collections

import com.example.unsplashhomework.domain.DigestPagingUseCase
import com.example.unsplashhomework.domain.GetDigestInfoUseCase
import com.example.unsplashhomework.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DigestViewModel @Inject constructor(
    private val digestPagingUseCase: DigestPagingUseCase
    ) : BaseViewModel() {

    fun getDigest() = digestPagingUseCase.getDigest().flow
}