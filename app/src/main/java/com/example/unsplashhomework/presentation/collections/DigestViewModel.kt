package com.example.unsplashhomework.presentation.collections

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.unsplashhomework.domain.model.Digest
import com.example.unsplashhomework.domain.DigestPagingUseCase
import com.example.unsplashhomework.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DigestViewModel @Inject constructor(private val digestPagingUseCase: DigestPagingUseCase): BaseViewModel() {

    val a = MutableSharedFlow<PagingData<Digest>>()

//    fun start(){
//        viewModelScope.launch(Dispatchers.IO){
//            getDigest()
//        }
//
//    }

    fun getDigest() {
        viewModelScope.launch(Dispatchers.IO) {
            digestPagingUseCase.getDigest().flow.onEach {
                a.emit(it)
            }.launchIn(viewModelScope)
        }
    }
}