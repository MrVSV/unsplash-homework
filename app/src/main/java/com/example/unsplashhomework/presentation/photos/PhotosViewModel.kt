package com.example.unsplashhomework.presentation.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PhotosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is first paging photos Fragment"
    }
    val text: LiveData<String> = _text
}