package com.chebureck.playlist.db

import android.content.SharedPreferences
import com.chebureck.playlist.network.api.spotify.SpotifyTokenProvider

class SpotifyTokenProviderImpl(
    private val sharedPreferences: SharedPreferences
) : SpotifyTokenProvider {
    override var token: String?
        get() = sharedPreferences.getString(TOKEN, null)
        set(value) {
            sharedPreferences.edit().putString(TOKEN, value).apply()
        }

    private companion object {
        const val TOKEN = "token"
    }
}