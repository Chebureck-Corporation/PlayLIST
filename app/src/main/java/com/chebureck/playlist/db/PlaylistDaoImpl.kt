package com.chebureck.playlist.db

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PlaylistDaoImpl(
    private val playlistDao: PlaylistDao
) {
    lateinit var playlistList: MutableLiveData<MutableList<PlaylistWrapper>>

    lateinit var trackList: MutableLiveData<MutableList<TrackWrapper>>

    fun getTrackById(id: Long): TrackWrapper {
        val trackEntity = playlistDao.getTrackById(id)
        return TrackWrapper(trackEntity)
    }

    fun getTracksByPlaylist(
        lifecycleOwner: LifecycleOwner,
        playlistId: Long
    ): LiveData<List<TrackWrapper>> {
        val tracksOfPlaylist = playlistDao.getTracksOfPlaylist(playlistId)
        val p = MutableLiveData<List<TrackWrapper>>()
        tracksOfPlaylist.observe(
            lifecycleOwner,
            { value ->
                p.value = value.map { TrackWrapper(it) }
            }
        )
        return p
    }

    fun getPlaylists(lifecycleOwner: LifecycleOwner): LiveData<List<PlaylistWrapper>> {
        val playlists = playlistDao.getPlaylists()
        val p = MutableLiveData<List<PlaylistWrapper>>()
        playlists.observe(
            lifecycleOwner,
            { value ->
                p.value = value.map { PlaylistWrapper(it) }
            }
        )
        return p
    }

    fun getPlaylistById(playlistId: Long): PlaylistWrapper {
        val playlist = playlistDao.getPlaylistById(playlistId)
        return PlaylistWrapper(playlist)
    }

    fun addPlaylist(playlistEntity: PlaylistEntity) {
        playlistDao.addPlaylist(playlistEntity)
        playlistList.value?.add(PlaylistWrapper(playlistEntity))
    }

    fun addTrack(trackEntity: TrackEntity) {
        playlistDao.addTrack(trackEntity)
        trackList.value?.add(TrackWrapper(trackEntity))
    }

    fun updatePlaylist(playlistEntity: PlaylistEntity) {
        playlistDao.updatePlaylist(playlistEntity)
    }

    fun deleteTrack(trackEntity: TrackEntity) {
        playlistDao.deleteTrack(trackEntity)
        val index = trackList.value?.indexOfFirst {
            it.id == trackEntity.id
        }
        if (index != null) {
            trackList.value?.removeAt(index)
        }
    }

    fun deletePlaylist(playlistEntity: PlaylistEntity) {
        playlistDao.deletePlaylist(playlistEntity)
        val index: Int? = playlistList.value?.indexOfFirst {
            it.id == playlistEntity.id
        }
        if (index != null) {
            playlistList.value?.removeAt(index)
        }
    }

    fun trackToPlaylist(playlistId: Long, trackId: Long) {
        playlistDao.trackToPlaylist(playlistId, trackId)
        findTrackById(trackId)?.let { findPlaylistById(playlistId)?.tracks?.value?.add(it) }
    }

    private fun findTrackById(trackId: Long): TrackWrapper? {
        val index: Int? = trackList.value?.indexOfFirst {
            it.id == trackId
        }
        return index?.let { trackList.value?.get(it) }
    }

    private fun findPlaylistById(playlistId: Long): PlaylistWrapper? {
        val index: Int? = playlistList.value?.indexOfFirst {
            it.id == playlistId
        }
        return index?.let { playlistList.value?.get(it) }
    }
}
