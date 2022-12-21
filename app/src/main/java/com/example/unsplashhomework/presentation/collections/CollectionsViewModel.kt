package com.example.unsplashhomework.presentation.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**это просто заготовка, переименовала Dashboard в Collections
 */
class CollectionsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is collections Fragment"
    }
    val text: LiveData<String> = _text
}