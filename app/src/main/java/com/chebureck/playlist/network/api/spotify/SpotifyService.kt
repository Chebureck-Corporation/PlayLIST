package com.chebureck.playlist.network.api.spotify

import com.chebureck.playlist.db.PlaylistWithTracks
import com.chebureck.playlist.network.api.spotify.`object`.SpotifyApiPlaylist
import com.chebureck.playlist.network.api.spotify.`object`.SpotifyApiPlaylistCreate
import com.chebureck.playlist.network.api.spotify.`object`.SpotifyUser
import com.chebureck.playlist.network.api.spotify.`object`.TrackUris
import com.chebureck.playlist.network.api.spotify.`object`.TracksInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Url

interface SpotifyService {
    @GET("/$API_VERSION/me/playlists")
    fun playlists(): Call<PagingObject<SpotifyApiPlaylist>>

    @GET
    fun tracks(@Url url: String): Call<PagingObject<TracksInfo>>

    @GET("/$API_VERSION/me")
    fun me(): Call<SpotifyUser>

    @Headers("Content-Type: application/json")
    @POST("/$API_VERSION/users/{user}/playlists")
    fun createPlaylist(
        @Path("user") userId: String,
        @Body playlistCreate: SpotifyApiPlaylistCreate,
    ): Call<SpotifyApiPlaylist>

    @Headers("Content-Type: application/json")
    @POST("/$API_VERSION/playlists/{id}/tracks")
    fun addPlaylistItems(
        @Path("id") id: String,
        @Body trackUris: TrackUris
    ): Call<Void>

    @Headers("Constent-Type: application/json")
    @PUT("/$API_VERSION/playlists/{playlist_id}")
    fun updatePlaylistName(
        @Path("playlist_id") playlistId: String,
        @Body playlistUpdate: SpotifyApiPlaylistCreate
    ): Call<Void>

    @DELETE("/$API_VERSION/playlists/{playlist_id}/followers")
    fun unfollowPlaylist(
        @Path("playlist_id") playlistId: String
    ): Call<Void>

    companion object {
        const val API_VERSION = "v1"
    }
}

fun SpotifyService.addPlaylistItems(
    playlistWithTracks: PlaylistWithTracks,
    id: String = playlistWithTracks.spotifyId!!,
    trackUris: TrackUris = TrackUris(playlistWithTracks.tracks.map { it.uri })
) = addPlaylistItems(id, trackUris)

data class PagingObject<T>(
    var items: List<T>
)
