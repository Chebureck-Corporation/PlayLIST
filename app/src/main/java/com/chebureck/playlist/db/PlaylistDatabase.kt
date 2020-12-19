package com.chebureck.playlist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chebureck.playlist.db.PlaylistDatabase.Companion.DATABASE_VERSION

@Database(
    entities = [Playlist::class, Track::class, PlaylistTrackCrossRef::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class PlaylistDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao

    companion object {
        const val DATABASE_NAME: String = "playlist_database"
        const val DATABASE_VERSION = 1
    }
}
