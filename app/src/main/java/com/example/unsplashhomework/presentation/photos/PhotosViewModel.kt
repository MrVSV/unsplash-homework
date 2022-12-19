package com.example.unsplashhomework.presentation.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplashhomework.data.api.ApiToken
import com.example.unsplashhomework.data.api.ResponseToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class PhotosViewModel : ViewModel() {

    val a = MutableSharedFlow<ResponseToken>()


    fun createToken(code:String){
        viewModelScope.launch (Dispatchers.IO) {
            a.emit( ApiToken.retrofit.getToken(code = code))
        }
    }
}