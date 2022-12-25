package com.example.unsplashhomework.presentation.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**это просто заготовка, переименовала Notifications в UserDto
 */
class UserViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is user Fragment"
    }
    val text: LiveData<String> = _text
}