package com.chebureck.playlist.network.states

import com.chebureck.playlist.network.ServiceManager

sealed class AuthorizationState

data class SuccessfulAuthState(
    val serviceManager: ServiceManager
) : AuthorizationState()

data class AuthErrorState(
    val error: Throwable?
) : AuthorizationState()
