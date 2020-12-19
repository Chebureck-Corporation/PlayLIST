@file:Suppress("RemoveExplicitTypeArguments")

package com.chebureck.playlist.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.chebureck.playlist.db.PlaylistDao
import com.chebureck.playlist.db.PlaylistDatabase
import com.chebureck.playlist.db.SpotifyTokenProviderImpl
import com.chebureck.playlist.mvvm.viewmodel.SpotifyViewModel
import com.chebureck.playlist.network.api.spotify.SpotifyAuthManager
import com.chebureck.playlist.network.api.spotify.SpotifyTokenProvider
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModule = module {
    viewModel<SpotifyViewModel> {
        SpotifyViewModel(get(), get(), get())
    }
}

const val SPOTIFY_CLIENT_ID = "d38b258e60eb46ae9b2b03cde1fd8329"
const val SPOTIFY_TOKEN_PROVIDER_NAME = "spotifyTokenProvider"
val spotifyModule = module {
    single<SpotifyAuthManager> {
        SpotifyAuthManager(SPOTIFY_CLIENT_ID)
    }
    single<SpotifyTokenProvider> {
        SpotifyTokenProviderImpl(get(named(SPOTIFY_TOKEN_PROVIDER_NAME)))
    }
    single<SharedPreferences>(named(SPOTIFY_TOKEN_PROVIDER_NAME)) {
        androidContext().getSharedPreferences(SPOTIFY_TOKEN_PROVIDER_NAME, Context.MODE_PRIVATE)
    }
}

val databaseModule = module {
    single<PlaylistDatabase> {
        Room.databaseBuilder(
            androidContext(),
            PlaylistDatabase::class.java,
            PlaylistDatabase.DATABASE_NAME
        ).build()
    }
    single<PlaylistDao> {
        get<PlaylistDatabase>().playlistDao()
    }
}