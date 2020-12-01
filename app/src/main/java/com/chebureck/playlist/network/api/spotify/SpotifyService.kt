package com.chebureck.playlist.network.api.spotify

import com.chebureck.playlist.db.Playlist
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SpotifyService {
    @GET("users/{userId}/playlists")
    fun playlists(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): Call<TPlaylistWrapper>

    @GET("me")
    fun me(
        @Header("Authorization") token: String
    ): Call<SpotifyUser>
}

class TPlaylistWrapper {
    lateinit var href: String
    lateinit var items: List<PlaylistWrapper>
}

class PlaylistWrapper : Playlist {
    @SerializedName("name")
    override lateinit var name: String
    @SerializedName("id")
    override lateinit var id: String
}
