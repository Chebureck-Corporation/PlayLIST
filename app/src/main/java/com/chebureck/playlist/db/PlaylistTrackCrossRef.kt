package com.chebureck.playlist.db

import androidx.room.Entity

@Entity(primaryKeys = ["playlistId", "trackId"])
data class PlaylistTrackCrossRef(
    val playlistId: Long,
    val trackId: Long
)
