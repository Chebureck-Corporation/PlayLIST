package com.chebureck.playlist.network.api.spotify

import com.chebureck.playlist.network.api.spotify.`object`.SpotifyApiPlaylist
import com.chebureck.playlist.network.api.spotify.`object`.TracksInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface SpotifyService {
    @GET("/$API_VERSION/me/playlists")
    fun playlists(
        @Header("Authorization") token: String
    ): Call<PagingObject<SpotifyApiPlaylist>>

    @GET
    fun tracks(
        @Url url: String,
        @Header("Authorization") token: String
    ): Call<PagingObject<TracksInfo>>

    companion object {
        const val API_VERSION = "v1"
    }
}

data class PagingObject<T>(
    var items: List<T>
)
