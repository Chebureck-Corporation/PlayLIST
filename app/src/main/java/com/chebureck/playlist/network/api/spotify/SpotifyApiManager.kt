package com.chebureck.playlist.network.api.spotify

import com.chebureck.playlist.db.Playlist
import com.chebureck.playlist.db.PlaylistWithTracks
import com.chebureck.playlist.db.Track
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SpotifyApiManager(
    spotifyToken: String
) {
    private val token = "Bearer $spotifyToken"

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spotify.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val spotifyService = retrofit.create(SpotifyService::class.java)

    private fun getTracks(href: String): List<Track> {
        val apiTracks = spotifyService.tracks(href, token)
            .execute()
            .body()?.items
            ?: return listOf()

        return apiTracks.map { subTrack ->
            val track = subTrack.track
            Track(track.id, track.name, track.artists.joinToString { it.name })
        }
    }

    fun getMyPlaylists(): List<PlaylistWithTracks>? {
        val apiPlaylists = spotifyService.playlists(token)
            .execute()
            .body()?.items

        return apiPlaylists?.let { playlist ->
            playlist.map {
                PlaylistWithTracks(
                    Playlist(it.id, it.imageUrl, it.name),
                    getTracks(it.tracks.href)
                )
            }
        }
    }
}
