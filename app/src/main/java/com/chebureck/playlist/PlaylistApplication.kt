package com.chebureck.playlist

import android.app.Application
import com.chebureck.playlist.di.databaseModule
import com.chebureck.playlist.di.repositoryModule
import com.chebureck.playlist.di.spotifyModule
import com.chebureck.playlist.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PlaylistApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PlaylistApplication)
            modules(viewModule, databaseModule, spotifyModule, repositoryModule)
        }
    }
}
