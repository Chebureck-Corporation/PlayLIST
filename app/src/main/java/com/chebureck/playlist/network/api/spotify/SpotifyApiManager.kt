package com.chebureck.playlist.network.api.spotify

import com.chebureck.playlist.db.Playlist
import com.chebureck.playlist.db.PlaylistWithTracks
import com.chebureck.playlist.db.Track
import com.chebureck.playlist.network.api.spotify.`object`.SpotifyApiPlaylistCreate

class SpotifyApiManager(
    private val spotifyService: SpotifyService
) {
    private fun getTracks(href: String): List<Track> {
        val apiTracks = spotifyService.tracks(href)
            .execute()
            .body()?.items
            ?: return listOf()

        return apiTracks.asSequence()
            .filter { it.track != null }
            .map { subTrack ->
                val track = subTrack.track!!
                Track(track.id, track.name, track.artists.joinToString { it.name }, track.uri)
            }.toList()
    }

    fun createPlaylist(playlist: PlaylistWithTracks): PlaylistWithTracks {
        val createdPlaylist = spotifyService.createPlaylist(
            getMe().id,
            SpotifyApiPlaylistCreate(playlist.name)
        ).execute().body() ?: throw Throwable("Created playlist can't be null")

        spotifyService.addPlaylistItems(playlist, createdPlaylist.id).execute()

        return PlaylistWithTracks(
            Playlist(createdPlaylist.id, playlist.imageUrl, playlist.name, playlist.id),
            playlist.tracks
        )
    }

    fun getMyPlaylists(): List<PlaylistWithTracks>? {
        val apiPlaylists = spotifyService.playlists()
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

    private fun getMe() = spotifyService.me().execute().body()!!
}
