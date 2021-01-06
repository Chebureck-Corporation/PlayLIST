package com.chebureck.playlist.network.api.spotify

import com.chebureck.playlist.db.Playlist
import com.chebureck.playlist.db.PlaylistWithTracks
import com.chebureck.playlist.db.Track
import com.chebureck.playlist.network.api.spotify.`object`.SpotifyApiPlaylistCreate
import com.chebureck.playlist.network.api.spotify.`object`.TrackUris
import com.chebureck.playlist.utils.ListsUtils

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
        if (playlist.tracks.size <= SPOTIFY_MAX_TRACKS_TO_UPLOAD) {
            spotifyService.addPlaylistItems(playlist, createdPlaylist.id).execute()
        } else {
            ListsUtils.createSublist(
                TrackUris(playlist.tracks.map { it.uri }).uris,
                SPOTIFY_MAX_TRACKS_TO_UPLOAD
            ).forEach {
                val trackUrisSublist = TrackUris(it)
                spotifyService.addPlaylistItems(playlist, createdPlaylist.id, trackUrisSublist).execute()
            }
        }

        return PlaylistWithTracks(
            Playlist(createdPlaylist.id, playlist.imageUrl, playlist.name, playlist.id),
            playlist.tracks
        )
    }

    fun updatePlaylistName(playlist: PlaylistWithTracks, name: String) {
        playlist.spotifyId?.let {
            spotifyService.updatePlaylistName(
                it,
                SpotifyApiPlaylistCreate(name)
            ).execute()
        }
    }

    fun unfollowPlaylist(playlist: PlaylistWithTracks) {
        spotifyService.unfollowPlaylist(playlist.spotifyId!!).execute()
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

    companion object {
        const val SPOTIFY_MAX_TRACKS_TO_UPLOAD = 100
    }
}
