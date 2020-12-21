package com.chebureck.playlist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    @Insert
    protected abstract fun insertTrackEntity(track: Track): Long

    @Insert
    protected abstract fun insertPlaylistTrackCrossRef(playlistTrackCrossRef: PlaylistTrackCrossRef)

    suspend fun insertPlaylist(playlist: PlaylistWithTracks) {
        withContext(Dispatchers.IO) {
            val playlistId = insertPlaylistEntity(playlist.playlist)
            for (track in playlist.tracks) {
                val trackId = insertTrackEntity(track)
                val crossRef = PlaylistTrackCrossRef(playlistId, trackId)
                insertPlaylistTrackCrossRef(crossRef)
            }
        }
    }
}
