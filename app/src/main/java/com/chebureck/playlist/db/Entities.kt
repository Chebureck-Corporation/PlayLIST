package com.chebureck.playlist.db

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

const val PLAYLIST_ENTITY: String = "playlists"

const val TRACK_ENTITY: String = "tracks"

const val PLAYLIST_TRACK_ENTITY: String = "playlists_tracks"

const val PLAYLIST_ID_FIELD: String = "playlist_id"

const val TRACK_ID_FIELD: String = "track_id"

@Entity(tableName = PLAYLIST_ENTITY)
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = PLAYLIST_ID_FIELD) val playlistId: Long,
    val name: String?
)

@Entity(tableName = TRACK_ENTITY)
data class TrackEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = TRACK_ID_FIELD) val trackId: Long,
    val name: String?,
    val author: String?
)

@Entity(tableName = PLAYLIST_TRACK_ENTITY, primaryKeys = [PLAYLIST_ID_FIELD, TRACK_ID_FIELD])
data class PlaylistAndTrackEntity(
    @ColumnInfo(name = PLAYLIST_ID_FIELD) val playlistId: Long,
    @ColumnInfo(name = TRACK_ID_FIELD) val trackId: Long
)

data class PlaylistWithTracks(
    @Embedded val playlist: PlaylistEntity,
    @Relation(
        parentColumn = PLAYLIST_ID_FIELD,
        entityColumn = TRACK_ID_FIELD,
        associateBy = Junction(PlaylistAndTrackEntity::class)
    )
    val tracks: LiveData<List<TrackEntity>>
)
