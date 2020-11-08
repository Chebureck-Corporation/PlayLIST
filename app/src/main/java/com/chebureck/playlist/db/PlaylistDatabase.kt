package com.chebureck.playlist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PlaylistEntity::class, TrackEntity::class, PlaylistAndTrackEntity::class],
    version = 1
)
abstract class PlaylistDatabase : RoomDatabase() {

    abstract fun playlistDao(): PlaylistDao

    companion object {

        private const val DATABASE_NAME = "db_name"

        var instance: PlaylistDatabase? = null

        fun getInstance(context: Context): PlaylistDatabase? {
            if (instance == null) {
                synchronized(PlaylistDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PlaylistDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return instance
        }

        fun destroyDatabase() {
            instance = null
        }
    }
}
