package com.chebureck.playlist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlaylistDao {
    @Query(
        "SELECT $TRACK_ENTITY.* FROM (SELECT * FROM $PLAYLIST_TRACK_ENTITY WHERE " +
            "$PLAYLIST_TRACK_ENTITY.playlist_id == :playlistId) as res_track_list LEFT JOIN " +
            "$TRACK_ENTITY on res_track_list.track_id = $TRACK_ENTITY.id"
    )
    fun getTracksOfPlaylist(playlistId: String): LiveData<List<TrackEntity>>

    @Query("SELECT * FROM $TRACK_ENTITY WHERE $ID_FIELD == :trackId")
    fun getTrackById(trackId: String): TrackEntity

    @Query("SELECT * FROM $PLAYLIST_ENTITY")
    fun getPlaylists(): LiveData<List<PlaylistEntity>>

    @Query("SELECT * FROM $PLAYLIST_ENTITY WHERE $ID_FIELD == :playlistId")
    fun getPlaylistById(playlistId: String): PlaylistEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlaylist(playlistEntity: PlaylistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTrack(trackEntity: TrackEntity)

    @Update
    fun updatePlaylist(playlistEntity: PlaylistEntity)

    @Delete
    fun deleteTrack(trackEntity: TrackEntity)

    @Delete
    fun deletePlaylist(playlistEntity: PlaylistEntity)

    @Query("INSERT INTO $PLAYLIST_TRACK_ENTITY ($PLAYLIST_ID_FIELD, $TRACK_ID_FIELD) VALUES (:playlistId, :trackId)")
    fun trackToPlaylist(playlistId: String, trackId: String)
}
