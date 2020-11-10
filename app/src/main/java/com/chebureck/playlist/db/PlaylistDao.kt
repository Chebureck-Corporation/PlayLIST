package com.chebureck.playlist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM $PLAYLIST_TRACK_ENTITY WHERE $PLAYLIST_ID_FIELD == :playlistId")
    fun getTracksOfPlaylist(playlistId: Long): LiveData<List<Track>>

    @Query("SELECT * FROM $TRACK_ENTITY WHERE $ID_FIELD == :trackId")
    fun getTrackById(trackId: Long): Track

    @Query("SELECT * FROM $PLAYLIST_ENTITY")
    fun getPlaylists(): LiveData<List<Playlist>>

    @Query("SELECT * FROM $PLAYLIST_ENTITY WHERE $ID_FIELD == :playlistId")
    fun getPlaylistById(playlistId: Long): Playlist

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlaylist(playlist: Playlist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTrack(track: Track)

    @Update
    fun updatePlaylist(playlist: Playlist)

    @Transaction
    @Delete
    fun deleteTrack(track: Track)

    @Transaction
    @Delete
    fun deletePlaylist(playlist: Playlist)

    @Query("INSERT INTO $PLAYLIST_TRACK_ENTITY VALUES (:playlistId, :trackId)")
    fun trackToPlaylist(playlistId: Long, trackId: Long)
}
