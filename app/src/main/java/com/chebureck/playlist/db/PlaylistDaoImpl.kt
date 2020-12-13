package com.chebureck.playlist.db

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PlaylistDaoImpl(
    private val playlistDao: PlaylistDao
) {
    fun getTrackById(id: String): Track {
        val trackEntity = playlistDao.getTrackById(id)
        return getTrackFromEntity(trackEntity)
    }

    fun getTracksByPlaylist(
        lifecycleOwner: LifecycleOwner,
        playlistId: String
    ): LiveData<List<Track>> {
        val tracksOfPlaylist = playlistDao.getTracksOfPlaylist(playlistId)
        val p = MutableLiveData<List<Track>>()
        tracksOfPlaylist.observe(
            lifecycleOwner,
            { value ->
                p.value = value.map { getTrackFromEntity(it) }
            }
        )
        return p
    }

    fun getPlaylists(lifecycleOwner: LifecycleOwner): LiveData<List<Playlist>> {
        val playlists = playlistDao.getPlaylists()
        val p = MutableLiveData<List<Playlist>>()
        playlists.observe(
            lifecycleOwner,
            { value ->
                p.value = value.map { getPlaylistFromEntity(it) }
            }
        )
        return p
    }

    fun getPlaylistById(playlistId: String): Playlist {
        val playlistEntity = playlistDao.getPlaylistById(playlistId)
        return getPlaylistFromEntity(playlistEntity)
    }

    fun addPlaylist(playlist: Playlist) {
        playlistDao.addPlaylist(playlistToEntity(playlist))
    }

    fun addTrack(track: Track) {
        playlistDao.addTrack(trackToEntity(track))
    }

    fun updatePlaylist(playlist: Playlist) {
        playlistDao.updatePlaylist(playlistToEntity(playlist))
    }

    fun deleteTrack(track: Track) {
        playlistDao.deleteTrack(trackToEntity(track))
    }

    fun deletePlaylist(playlistEntity: Playlist) {
        playlistDao.deletePlaylist(playlistToEntity(playlistEntity))
    }

    fun trackToPlaylist(playlistId: String, trackId: String) {
        playlistDao.trackToPlaylist(playlistId, trackId)
    }

    private companion object {
        fun getTrackFromEntity(trackEntity: TrackEntity) =
            Track(trackEntity.name, trackEntity.author, trackEntity.id)

        fun getPlaylistFromEntity(playlistEntity: PlaylistEntity) =
            Playlist(playlistEntity.name, playlistEntity.imageUrl, playlistEntity.id)

        fun trackToEntity(track: Track) =
            TrackEntity(track.name, track.author, track.id)

        fun playlistToEntity(playlist: Playlist) =
            PlaylistEntity(playlist.id, playlist.imageUrl, playlist.name)
    }
}
