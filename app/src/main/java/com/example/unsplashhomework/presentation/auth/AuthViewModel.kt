package com.example.unsplashhomework.presentation.auth

import androidx.lifecycle.viewModelScope
import com.example.unsplashhomework.data.api.ApiToken
import com.example.unsplashhomework.data.state.LoadState
import com.example.unsplashhomework.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val apiToken: ApiToken) : BaseViewModel() {

    private val _token = MutableSharedFlow<String>()
    val token = _token.asSharedFlow()

    private var accessToken = PLUG

    init {
        //_loadState.value = LoadState.START
        startState()
    }


    /**вот тут вообще лишнее. Для чего у нас в base view model  стоит стайт флоу а не шаред флоу?
     * вопервых если в большенство фрагментов лоад стейт старт то можно его прям стейте стартовым и
     * указать protected val _loadState =MutableStateFlow(LoadState.START) в байс вью моедл
     * во вторых _loadState.value = LoadState.START не требует корутиныпоэтому смотрите выше
     * без функции просто в одну строчку пишем нужный стейт, если он отлечается от базового 4 строчки
     * просто лишние*/
    private fun startState() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadState.emit(LoadState.START)
        }
    }
    
    fun createToken(code: String) {
        if (code != PLUG && accessToken != START_REQUEST)
            viewModelScope.launch(Dispatchers.IO) {
                _loadState.emit(LoadState.LOADING)
                accessToken = START_REQUEST
                accessToken = try {
                    apiToken.getToken(code = code).access_token
                } catch (t: Exception) {
                    _loadState.emit(LoadState.ERROR.apply { message = t.message.toString() })
                    PLUG
                }
                _token.emit(accessToken)
                _loadState.emit(LoadState.SUCCESS)
            }
    }

    companion object {
        const val PLUG = ""
        const val START_REQUEST = "start_request"
    }
}