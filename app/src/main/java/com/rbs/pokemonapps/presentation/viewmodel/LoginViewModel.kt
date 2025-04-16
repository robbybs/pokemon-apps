package com.rbs.pokemonapps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbs.pokemonapps.data.local.datastore.DataStoreManager
import com.rbs.pokemonapps.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCase,
    private val storeManager: DataStoreManager
) : ViewModel() {
    private var _viewState = MutableSharedFlow<Boolean>()
    val viewState = _viewState.asSharedFlow()

    fun validate(username: String, password: String): Boolean = username.isNotEmpty() && password.isNotEmpty()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = useCase.login(username, password)
            if (result) {
                storeManager.apply {
                    saveLoginState(true)
                    saveUserId(username)
                }
            }
            _viewState.emit(result)
        }
    }
}