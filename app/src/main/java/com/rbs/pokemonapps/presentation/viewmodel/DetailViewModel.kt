package com.rbs.pokemonapps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.domain.model.PokeDetailDomain
import com.rbs.pokemonapps.domain.usecase.DetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: DetailUseCase
) : ViewModel() {
    private var _viewState = MutableStateFlow<ResultState<PokeDetailDomain>>(ResultState.Loading)
    val viewState = _viewState.asStateFlow()

    fun getDetail(id: Int) {
        viewModelScope.launch {
            when (val result = useCase.getDetail(id)) {
                is ResultState.Loading -> Unit
                is ResultState.Success ->  _viewState.value = ResultState.Success(result.data)
                is ResultState.Error -> _viewState.value = ResultState.Error(result.message)
            }
        }
    }
}