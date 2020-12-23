@file:Suppress("RemoveExplicitTypeArguments", "EXPERIMENTAL_API_USAGE")

package com.chebureck.playlist.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.chebureck.playlist.db.PlaylistDao
import com.chebureck.playlist.db.PlaylistDatabase
import com.chebureck.playlist.db.SpotifyTokenProviderImpl
import com.chebureck.playlist.mvvm.repository.PlaylistRepository
import com.chebureck.playlist.mvvm.viewmodel.PlaylistCreateViewModel
import com.chebureck.playlist.mvvm.viewmodel.SpotifyViewModel
import com.chebureck.playlist.network.api.spotify.SpotifyApiManager
import com.chebureck.playlist.network.api.spotify.SpotifyAuthManager
import com.chebureck.playlist.network.api.spotify.SpotifyService
import com.chebureck.playlist.network.api.spotify.SpotifyTokenProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {
    single<PlaylistRepository> {
        PlaylistRepository(get())
    }
}

val viewModule = module {
    viewModel<SpotifyViewModel> {
        SpotifyViewModel(get(), get(), get())
    }
    viewModel<PlaylistCreateViewModel> {
        PlaylistCreateViewModel(get())
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
    single<OkHttpClient> { (token: String) ->
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .method(original.method, original.body)
                    .build()

                chain.proceed(request)
            }
            addInterceptor(interceptor)
        }.build()
    }
    factory<SpotifyService> { (token: String) ->
        Retrofit.Builder().baseUrl("https://api.spotify.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get { parametersOf(token) })
            .build()
            .create(SpotifyService::class.java)
    }
    factory<SpotifyApiManager> { (token: String) ->
        SpotifyApiManager(get { parametersOf(token) })
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