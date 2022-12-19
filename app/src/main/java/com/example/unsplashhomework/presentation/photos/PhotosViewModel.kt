package com.example.unsplashhomework.presentation.photos

import android.util.Log
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
            try {
                Log.e("Kart","start")
                val b = ApiToken.retrofit.getToken(code = code)
                Log.e("Kart","${b.access_token}")
                a.emit( b)
            } catch (e:Exception){
                Log.e("Kart",e.message.toString())
            }

        }
    }
}