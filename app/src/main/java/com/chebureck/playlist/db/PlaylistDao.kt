package com.chebureck.playlist.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class PlaylistDao {
    @Transaction
    @Query("SELECT * FROM Playlist")
    abstract fun getPlaylists(): LiveData<List<PlaylistWithTracks>>

    @Transaction
    @Query("SELECT * FROM Playlist WHERE Playlist.playlistId == :playlistId")
    abstract fun getPlaylistById(playlistId: String): PlaylistWithTracks

    @Insert
    protected abstract fun insertPlaylistEntity(playlist: Playlist): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insertTrackEntity(track: Track): Long

    @Insert
    protected abstract fun insertPlaylistTrackCrossRef(playlistTrackCrossRef: PlaylistTrackCrossRef)

    @Delete
    protected abstract fun deletePlaylist(playlist: Playlist)

    @Delete
    protected abstract fun deletePlaylistTrackCrossRef(playlistTrackCrossRef: PlaylistTrackCrossRef)

    @Transaction
    open fun deletePlaylistWithTracks(playlist: PlaylistWithTracks) {
        deletePlaylist(playlist.playlist)
        for (track in playlist.tracks) {
            val crossRef = PlaylistTrackCrossRef(playlist.id, track.trackId)
            deletePlaylistTrackCrossRef(crossRef)
        }
    }

    @Transaction
    open fun insertPlaylist(playlist: PlaylistWithTracks) {
        val playlistId = insertPlaylistEntity(playlist.playlist)
        for (track in playlist.tracks) {
            val trackId = insertTrackEntity(track)
            val crossRef = PlaylistTrackCrossRef(playlistId, trackId)
            insertPlaylistTrackCrossRef(crossRef)
        }
    }
}
