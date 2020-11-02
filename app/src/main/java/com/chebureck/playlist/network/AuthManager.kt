package com.chebureck.playlist.network

import androidx.annotation.IntDef

interface AuthManager {
    fun auth(@ServiceType serviceType: Int, authResultListener: AuthResultListener)
}

interface AuthResultListener {
    fun onAuthResult(
        serviceManager: ServiceManager?,
        @ServiceType serviceType: Int,
        @AuthResponseStatus responseStatus: Int
    )
}

@IntDef(
    AuthResponseStatus.OK,
    AuthResponseStatus.UNKNOWN_ERROR
)
@Retention(AnnotationRetention.SOURCE)
annotation class AuthResponseStatus {
    companion object AuthResponseStatus {
        const val OK = 0
        const val UNKNOWN_ERROR = 1
    }
}
