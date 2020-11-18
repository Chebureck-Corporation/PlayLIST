package com.chebureck.playlist.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlaylistEntity::class, TrackEntity::class, PlaylistAndTrack::class],
    version = 1
)
abstract class PlaylistDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao?

    companion object {
        const val DATABASE_NAME: String = "playlist_database"
    }
}
