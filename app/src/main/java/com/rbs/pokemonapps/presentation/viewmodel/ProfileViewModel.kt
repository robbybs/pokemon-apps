package com.rbs.pokemonapps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.data.local.datastore.DataStoreManager
import com.rbs.pokemonapps.domain.model.UserDomain
import com.rbs.pokemonapps.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase,
    private val dataStore: DataStoreManager
) : ViewModel() {
    private var _viewState = MutableStateFlow<ResultState<UserDomain>>(ResultState.Loading)
    val viewState = _viewState.asStateFlow()

    private var _logoutState = MutableSharedFlow<Boolean>()
    val logoutState = _logoutState.asSharedFlow()

    init {
        getDetail()
    }

    private fun getDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.getUserId.collectLatest {
                val result = useCase.getProfile(it.orEmpty())
                _viewState.value = result
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.clearLoginState()
            _logoutState.emit(true)
        }
    }
}