package com.chebureck.playlist.network.api.spotify

import com.chebureck.playlist.db.Playlist
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SpotifyApiManager(
    spotifyToken: String
) {
    private val token = "Bearer $spotifyToken"

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spotify.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val spotifyService = retrofit.create(SpotifyService::class.java)

    fun getMe(): SpotifyUser? {
        return spotifyService.me(token)
            .execute()
            .body()
    }

    fun getPlaylists(userId: String): List<Playlist>? {
        return spotifyService.playlists(token, userId)
            .execute()
            .body()?.items
    }
}
