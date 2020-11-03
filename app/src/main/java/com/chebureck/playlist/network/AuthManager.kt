package com.chebureck.playlist.network

import com.chebureck.playlist.network.states.AuthorizationState

interface AuthManager {
    fun auth(
        @ServiceType serviceType: Int,
        authorizationListener: (authorizationState: AuthorizationState) -> Unit
    )
}
