package com.chebureck.playlist

import android.app.Application
import androidx.room.Room
import com.chebureck.playlist.db.PlaylistDaoImpl
import com.chebureck.playlist.db.PlaylistDatabase

class PlaylistApplication : Application() {
    lateinit var playlistDatabase: PlaylistDatabase
    lateinit var playlistDao: PlaylistDaoImpl

    override fun onCreate() {
        super.onCreate()
        synchronized(this) {
            playlistDatabase = Room.databaseBuilder(
                applicationContext,
                PlaylistDatabase::class.java,
                PlaylistDatabase.DATABASE_NAME
            ).build()
            playlistDao = PlaylistDaoImpl(playlistDatabase.playlistDao()!!)
        }
    }
}
