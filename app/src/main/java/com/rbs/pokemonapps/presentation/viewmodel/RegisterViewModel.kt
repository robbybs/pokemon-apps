package com.rbs.pokemonapps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.rbs.pokemonapps.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: RegisterUseCase
) : ViewModel() {

}