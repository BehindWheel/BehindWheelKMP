package com.egoriku.grodnoroads.auth

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.extensions.coroutines.runCatchingCancellable
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

interface Auth {

    val isSignedIn: StateFlow<Boolean>

    suspend fun signIn(email: String, password: String): ResultOf<Unit>
    suspend fun signOut()
}

internal class AuthImpl(
    private val firebaseAuth: FirebaseAuth
) : Auth {

    override val isSignedIn: StateFlow<Boolean>
        field = MutableStateFlow(firebaseAuth.currentUser != null)

    override suspend fun signIn(email: String, password: String): ResultOf<Unit> = withContext(Dispatchers.IO) {
        runCatchingCancellable {
            firebaseAuth.signInWithEmailAndPassword(email, password)
            isSignedIn.value = true
            ResultOf.Success(Unit)
        }.getOrElse {
            ResultOf.Failure(it)
        }
    }

    override suspend fun signOut() {
        withContext(Dispatchers.IO) {
            firebaseAuth.signOut()
            isSignedIn.value = false
        }
    }
}
