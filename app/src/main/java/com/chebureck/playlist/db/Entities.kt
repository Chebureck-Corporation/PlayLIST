package com.chebureck.playlist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

const val PLAYLIST_ENTITY: String = "playlists"

const val TRACK_ENTITY: String = "tracks"

const val PLAYLIST_TRACK_ENTITY: String = "playlists_tracks"

const val ID_FIELD: String = "id"

const val PLAYLIST_ID_FIELD: String = "playlist_id"

const val TRACK_ID_FIELD: String = "track_id"

@Entity(tableName = PLAYLIST_ENTITY)
data class PlaylistEntity(
    @PrimaryKey val id: String,
    var imageUrl: String?,
    var name: String
)

@Entity(tableName = TRACK_ENTITY)
data class TrackEntity(
    @PrimaryKey val id: String,
    val name: String,
    val author: String
)

@Entity(
    tableName = PLAYLIST_TRACK_ENTITY,
    foreignKeys = [
        ForeignKey(
            entity = PlaylistEntity::class,
            parentColumns = [ID_FIELD],
            childColumns = [PLAYLIST_ID_FIELD],
            onDelete = CASCADE
        ), ForeignKey(
            entity = TrackEntity::class,
            parentColumns = [ID_FIELD],
            childColumns = [TRACK_ID_FIELD],
            onDelete = CASCADE
        )
    ]
)
data class PlaylistAndTrack(
    @PrimaryKey val id: String,
    @ColumnInfo(name = PLAYLIST_ID_FIELD) val playlistId: String,
    @ColumnInfo(name = TRACK_ID_FIELD) val trackId: String
)
