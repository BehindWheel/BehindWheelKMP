package com.egoriku.grodnoroads.settings.debugtools.domain.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.egoriku.grodnoroads.auth.Auth
import com.egoriku.grodnoroads.extensions.common.ResultOf
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun buildAuthComponent(
    componentContext: ComponentContext
): AuthComponent = AuthComponentImpl(componentContext)

internal class AuthComponentImpl(
    componentContext: ComponentContext
) : AuthComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val auth: Auth by inject()
    private val componentScope = coroutineScope()

    override val isSignedIn: StateFlow<Boolean> = auth.isSignedIn

    override suspend fun signIn(email: String, password: String): ResultOf<Unit> = auth.signIn(email, password)

    override fun signOut() {
        componentScope.launch {
            auth.signOut()
        }
    }
}
