package com.chebureck.playlist.mvvm.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chebureck.playlist.db.PlaylistWithTracks
import com.chebureck.playlist.db.PlaylistDao
import com.chebureck.playlist.network.api.spotify.SpotifyApiManager
import com.chebureck.playlist.network.api.spotify.SpotifyAuthManager
import com.chebureck.playlist.network.api.spotify.SpotifyTokenProvider
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpotifyViewModel(
    private val spotifyAuthManager: SpotifyAuthManager,
    private val tokenProvider: SpotifyTokenProvider,
    private val playlistDao: PlaylistDao
) : ViewModel() {
    private val tokenData = MutableLiveData<String?>(token)
    private val playlists = MutableLiveData<List<PlaylistWithTracks>?>()
    private var spotifyApiManager: SpotifyApiManager? = null

    private var token: String?
        get() = tokenProvider.token
        set(value) {
            tokenProvider.token = value
            refreshToken(value)
        }

    init {
        refreshToken(token)
    }

    fun getToken(): LiveData<String?> = tokenData
    @Deprecated("Used only in one case")
    fun getSavedToken() = token
    fun getPlaylists(): LiveData<List<PlaylistWithTracks>?> = playlists

    fun onLoginResponse(response: AuthorizationResponse?) {
        token = when (response?.type) {
            AuthorizationResponse.Type.TOKEN -> {
                response.accessToken
            }
            else -> {
                null
            }
        }
    }

    fun requestLogin(activityLoginListener: Activity) {
        if (token != null) {
            tokenData.value = token
            return
        }
        spotifyAuthManager.login(activityLoginListener, true)
    }

    fun requestMyPlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            val spotifyPlaylists = spotifyApiManager?.getMyPlaylists()
            if (spotifyPlaylists != null) {
                playlists.postValue(spotifyPlaylists)
            } else {
                launch(Dispatchers.Main) {
                    token = null
                }
            }
        }
    }

    fun savePlaylist(playlist: PlaylistWithTracks) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistDao.insertPlaylist(playlist)
            val savedPlaylists = playlistDao.getPlaylists()
            playlists.postValue(savedPlaylists)
        }
    }

    private fun refreshToken(value: String?) {
        spotifyApiManager = value?.let { SpotifyApiManager(it) }
        tokenData.value = value
    }
}