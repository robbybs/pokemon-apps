package com.rbs.pokemonapps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.domain.model.PokeItemDomain
import com.rbs.pokemonapps.domain.usecase.PokeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val useCase: PokeUseCase
) : ViewModel() {
    private var _viewState = MutableStateFlow<ResultState<PagingData<PokeItemDomain>>>(ResultState.Loading)
    val viewState = _viewState.asStateFlow()

    private val _searchResults = MutableSharedFlow<ResultState<List<PokeItemDomain>>>()
    val searchResults = _searchResults.asSharedFlow()

    fun fetchData() {
        viewModelScope.launch {
            useCase.getListPoke()
                .cachedIn(viewModelScope)
                .onStart {
                    _viewState.value = ResultState.Loading
                }
                .catch {
                    _viewState.value = ResultState.Error(it.message.orEmpty())
                }
                .collectLatest {
                    _viewState.value = ResultState.Success(it)
                }
        }
    }

    fun getAllQuery(query: String) {
        viewModelScope.launch {
            _searchResults.emit(ResultState.Loading)
            when (val result = useCase.getAllPoke(query)) {
                is ResultState.Loading -> Unit
                is ResultState.Success ->  _searchResults.emit(ResultState.Success(result.data.listPoke))
                is ResultState.Error -> _searchResults.emit(ResultState.Error(result.message))
            }
        }
    }
}