package com.chebureck.playlist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val PLAYLISTS_ENTITY: String = "playlists"

const val TRACKS_ENTITY: String = "tracks"

const val PLAYLISTS_TRACKS_ENTITY: String = "playlists_tracks"

@Entity(tableName = PLAYLISTS_ENTITY)
data class Playlists(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String?
)

@Entity(tableName = TRACKS_ENTITY)
data class Tracks(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String?,
    val author: String?
)

@Entity(tableName = PLAYLISTS_TRACKS_ENTITY)
data class PlaylistsAndTracks(
    @ColumnInfo(name = "playlist_id") val playlistId: Int,
    @ColumnInfo(name = "track_id") val trackId: Int
)
