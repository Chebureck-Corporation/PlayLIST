package com.chebureck.playlist.db

import androidx.lifecycle.LiveData

class PlaylistDaoImpl(
    private val playlistDao: PlaylistDao
) {
    fun getTrackById(id: Long): TrackWrapper {
        val trackEntity = playlistDao.getTrackById(id)
        return TrackWrapper(trackEntity)
    }

    fun getTracksByPlaylist(playlistId: Long): LiveData<List<TrackWrapper>> {
        val tracksOfPlaylist = playlistDao.getTracksOfPlaylist(playlistId)
        lateinit var wrapperList: LiveData<MutableList<TrackWrapper>>
        // завис
    }
}
