package com.rbs.pokemonapps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbs.pokemonapps.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: RegisterUseCase
) : ViewModel() {
    private var _viewState = MutableSharedFlow<Boolean>()
    val viewState = _viewState.asSharedFlow()

    fun validate(name: String, username: String, password: String): Boolean = name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()

    fun register(name: String, username: String, password: String) {
        viewModelScope.launch {
            val result = useCase.registerUser(name, username, password)
            _viewState.emit(result)
        }
    }
}