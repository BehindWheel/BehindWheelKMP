package com.egoriku.grodnoroads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egoriku.grodnoroads.domain.model.Camera
import com.egoriku.grodnoroads.domain.usecase.CameraUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CameraViewModel(
    private val useCase: CameraUseCase
) : ViewModel() {

    private val _stationary = MutableStateFlow<List<Camera>>(emptyList())
    val stationary = _stationary.asStateFlow()

    init {
        viewModelScope.launch {
            _stationary.emit(useCase.loadStationary())
        }
    }
}