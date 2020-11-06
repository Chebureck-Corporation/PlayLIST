package com.chebureck.playlist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaylistDao : Playlist {
    @Query("SELECT * FROM $PLAYLISTS_TRACKS_ENTITY WHERE playlist_id == :playlistId")
    suspend fun getTracksOfPlaylist(playlistId: Int): List<Tracks>

    @Query("SELECT * FROM $TRACKS_ENTITY WHERE id == :trackId")
    suspend fun getTrackById(trackId: Int): Tracks

    @Query("SELECT * FROM $PLAYLISTS_ENTITY")
    suspend fun getPlaylists(): List<Playlists>

    @Query("SELECT * FROM $PLAYLISTS_ENTITY WHERE id == :playlistId")
    suspend fun getPlaylistById(playlistId: Int): Playlists

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlaylist(playlist: Playlists)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrack(track: Tracks)

    @Query(
        "UPDATE $PLAYLISTS_ENTITY SET id = :newName; " +
            "UPDATE $PLAYLISTS_TRACKS_ENTITY SET playlist_id = :newName"
    )
    suspend fun setPlaylistName(newName: String)

    @Query(
        "DELETE FROM $TRACKS_ENTITY WHERE id IN (:trackIdList); " +
            "DELETE FROM $PLAYLISTS_TRACKS_ENTITY WHERE track_id IN (:trackIdList)"
    )
    suspend fun removeTracks(trackIdList: List<Int>)

    @Query(
        "DELETE FROM $PLAYLISTS_ENTITY WHERE id IN (:playlistIdList); " +
            "DELETE FROM $PLAYLISTS_TRACKS_ENTITY WHERE playlist_id IN (:playlistIdList)"
    )
    suspend fun removePlaylists(playlistIdList: List<Int>)

    @Query(
        "DELETE FROM $TRACKS_ENTITY WHERE id == :trackId; " +
            "DELETE FROM $PLAYLISTS_TRACKS_ENTITY WHERE track_id == :trackId"
    )
    suspend fun removeTrack(trackId: Int)

    @Query(
        "DELETE FROM $PLAYLISTS_ENTITY WHERE id == :playlistId; " +
            "DELETE FROM $PLAYLISTS_TRACKS_ENTITY WHERE playlist_id == :playlistId"
    )
    suspend fun removePlaylist(playlistId: Int)

    @Query("INSERT INTO $PLAYLISTS_TRACKS_ENTITY VALUES (:playlistId, :trackId)")
    suspend fun trackToPlaylist(trackId: Int, playlistId: Int)
}
