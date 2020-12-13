package com.chebureck.playlist.network.api.spotify

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
    lateinit var items: List<SpotifyAPIPlaylist>
}

class SpotifyAPIPlaylist(
    @SerializedName("name")
    var name: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("images")
    private var images: List<Image>?
) {
    val imageUrl: String?
        get() = images?.let { it[0].url }
}

data class Image(
    val url: String
)
