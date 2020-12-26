package com.chebureck.playlist.mvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chebureck.playlist.db.PlaylistDao
import com.chebureck.playlist.db.PlaylistWithTracks
import com.chebureck.playlist.network.api.spotify.SpotifyApiManager
import com.chebureck.playlist.utils.CombinedLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaylistRepository(
    private val playlistDao: PlaylistDao,
    private var spotifyApiManager: SpotifyApiManager? = null
) {
    private var spotifyLoadedSuccessfully = true
    private var spotifyPlaylists = MutableLiveData<List<PlaylistWithTracks>>()
    private val localPlaylists = playlistDao.getPlaylists()

    private val playlists =
        CombinedLiveData(spotifyPlaylists, localPlaylists) { spotifyData, localData ->
            PlaylistsCallback(spotifyData, localData, spotifyLoadedSuccessfully)
        }

    fun getPlaylists(): LiveData<PlaylistsCallback> = playlists

    suspend fun requestPlaylists() {
        withContext(Dispatchers.IO) {
            requestSpotifyPlaylists()
        }
    }

    suspend fun saveLocalPlaylist(playlist: PlaylistWithTracks) {
        withContext(Dispatchers.IO) {
            playlistDao.insertPlaylist(playlist)
        }
    }

    private fun requestSpotifyPlaylists() {
        val loadedSpotifyPlaylists = spotifyApiManager?.getMyPlaylists()
        spotifyLoadedSuccessfully = loadedSpotifyPlaylists != null
        spotifyPlaylists.postValue(loadedSpotifyPlaylists)
    }

    fun setSpotifyApiManager(spotifyApiManager: SpotifyApiManager) {
        this.spotifyApiManager = spotifyApiManager
    }

    suspend fun createSpotifyPlaylist(playlist: PlaylistWithTracks) {
        withContext(Dispatchers.IO) {
            val createdPlaylist = spotifyApiManager?.createPlaylist(playlist)!!
            playlistDao.deletePlaylistWithTracks(createdPlaylist)
        }
    }

    suspend fun updatePlaylistName(playlist: PlaylistWithTracks, name: String) {
        withContext(Dispatchers.IO){
            if (playlist.spotifyId != null){
                    spotifyApiManager?.updatePlaylistName(playlist, name)
                requestPlaylists()
            } else{
                playlistDao.updatePlaylist(playlist, name)
            }
        }
    }

    suspend fun unfollowPlaylist(playlist: PlaylistWithTracks) {
        withContext(Dispatchers.IO){
            if (playlist.spotifyId != null){
                spotifyApiManager?.unfollowPlaylist(playlist)
            } else{
                playlistDao.deletePlaylistWithTracks(playlist)
            }
        }
    }
}

data class PlaylistsCallback(
    val spotifyPlaylists: List<PlaylistWithTracks>?,
    val localPlaylists: List<PlaylistWithTracks>?,
    val spotifyLoadedSuccessfully: Boolean
) {
    val playlists: List<PlaylistWithTracks>

    init {
        val spotifyData = spotifyPlaylists ?: listOf()
        val localData = localPlaylists ?: listOf()
        playlists = localData.toMutableList().apply { addAll(spotifyData) }
    }
}

