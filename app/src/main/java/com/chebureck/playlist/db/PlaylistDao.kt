package com.chebureck.playlist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM $PLAYLIST_TRACK_ENTITY WHERE $PLAYLIST_ID_FIELD == :playlistId")
    fun getTracksOfPlaylist(playlistId: Long): LiveData<List<TrackEntity>>

    @Transaction
    @Query("SELECT * FROM $PLAYLIST_ENTITY")
    fun getPlaylistsWithTracks(): LiveData<List<PlaylistWithTracks>>

    @Query("SELECT * FROM $TRACK_ENTITY WHERE $TRACK_ID_FIELD == :trackId")
    suspend fun getTrackById(trackId: Long): TrackEntity

    @Query("SELECT * FROM $PLAYLIST_ENTITY")
    fun getPlaylists(): LiveData<List<PlaylistEntity>>

    @Query("SELECT * FROM $PLAYLIST_ENTITY WHERE $PLAYLIST_ID_FIELD == :playlistId")
    suspend fun getPlaylistById(playlistId: Long): PlaylistEntity

    @Query("SELECT name FROM $TRACK_ENTITY  WHERE $TRACK_ID_FIELD == :trackId")
    suspend fun getTrackNameById(trackId: Long): String

    @Query("SELECT name from $PLAYLIST_ENTITY WHERE $PLAYLIST_ID_FIELD == :playlistId")
    suspend fun getPlaylistNameById(playlistId: Long): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlaylist(playlist: PlaylistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrack(track: TrackEntity)

    @Transaction
    @Query("UPDATE $PLAYLIST_ENTITY SET name = :newName WHERE $PLAYLIST_ID_FIELD == :playlistId")
    suspend fun setPlaylistName(newName: String, playlistId: Long)

    @Transaction
    @Query("DELETE FROM $TRACK_ENTITY WHERE $TRACK_ID_FIELD IN (:trackIdList)")
    suspend fun removeTracks(trackIdList: List<Long>)

    @Transaction
    @Query("DELETE FROM $PLAYLIST_ENTITY WHERE $PLAYLIST_ID_FIELD IN (:playlistIdList)")
    suspend fun removePlaylists(playlistIdList: List<Long>)

    @Transaction
    @Query("DELETE FROM $TRACK_ENTITY WHERE $TRACK_ID_FIELD == :trackId")
    suspend fun removeTrack(trackId: Long)

    @Transaction
    @Query("DELETE FROM $PLAYLIST_ENTITY WHERE $PLAYLIST_ID_FIELD == :playlistId")
    suspend fun removePlaylist(playlistId: Long)

    @Query("INSERT INTO $PLAYLIST_TRACK_ENTITY VALUES (:playlistId, :trackId)")
    suspend fun trackToPlaylist(playlistId: Long, trackId: Long)
}
