package com.chebureck.playlist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class PlaylistDao {
    @Transaction
    @Query("SELECT * FROM Playlist")
    abstract fun getPlaylists(): List<PlaylistWithTracks>

    @Transaction
    @Query("SELECT * FROM Playlist WHERE Playlist.playlistId == :playlistId")
    abstract fun getPlaylistById(playlistId: String): PlaylistWithTracks

    @Insert
    protected abstract fun insertPlaylistEntity(playlist: Playlist)

    @Insert
    protected abstract fun insertTrackEntity(track: Track)

    @Insert
    protected abstract fun insertPlaylistTrackCrossRef(playlistTrackCrossRef: PlaylistTrackCrossRef)

    fun insertPlaylist(playlist: PlaylistWithTracks) {
        insertPlaylistEntity(playlist.playlist)
        for (track in playlist.tracks) {
            insertTrackEntity(track)
            val crossRef = PlaylistTrackCrossRef(playlist.id, track.trackId)
            insertPlaylistTrackCrossRef(crossRef)
        }
    }
}
