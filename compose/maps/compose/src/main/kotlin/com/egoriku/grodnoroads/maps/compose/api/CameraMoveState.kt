package com.egoriku.grodnoroads.maps.compose.api

sealed interface CameraMoveState {
    data object UserGesture : CameraMoveState
    data object Animating : CameraMoveState
    data object Idle : CameraMoveState
}