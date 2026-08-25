package com.egoriku.grodnoroads.settings.debugtools.domain.auth

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.extensions.common.ResultOf
import kotlinx.coroutines.flow.StateFlow

@Stable
interface AuthComponent {

    val isSignedIn: StateFlow<Boolean>

    suspend fun signIn(email: String, password: String): ResultOf<Unit>
    fun signOut()
}
