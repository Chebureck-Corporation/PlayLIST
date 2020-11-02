package com.chebureck.playlist.network

import androidx.annotation.IntDef

@IntDef(ServiceType.SPOTIFY)
@Retention(AnnotationRetention.SOURCE)
annotation class ServiceType {
    companion object ServiceType {
        const val SPOTIFY = 0
    }
}
