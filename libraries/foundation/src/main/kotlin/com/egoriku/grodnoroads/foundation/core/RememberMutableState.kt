package com.egoriku.grodnoroads.foundation.core

import androidx.compose.runtime.*

@Composable
inline fun <T> rememberMutableState(
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    crossinline calculation: @DisallowComposableCalls () -> T
) = remember { mutableStateOf(calculation(), policy) }

@Composable
inline fun rememberMutableIntState(
    crossinline value: @DisallowComposableCalls () -> Int
) = remember { mutableIntStateOf(value()) }

@Composable
inline fun <T> rememberMutableState(
    key1: Any?,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    crossinline calculation: @DisallowComposableCalls () -> T
) = remember(key1) { mutableStateOf(calculation(), policy) }

@Composable
inline fun <T> rememberMutableState(
    key1: Any?,
    key2: Any?,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    crossinline calculation: @DisallowComposableCalls () -> T
) = remember(key1, key2) { mutableStateOf(calculation(), policy) }