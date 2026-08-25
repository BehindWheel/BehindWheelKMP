package com.egoriku.grodnoroads.settings.debugtools.domain.auth

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.extensions.coroutines.stateFlowOf
import kotlinx.coroutines.flow.StateFlow

class AuthComponentPreview : AuthComponent {

    override val isSignedIn: StateFlow<Boolean> = stateFlowOf { false }

    override suspend fun signIn(email: String, password: String): ResultOf<Unit> = ResultOf.Success(Unit)
    override fun signOut() = Unit
}
