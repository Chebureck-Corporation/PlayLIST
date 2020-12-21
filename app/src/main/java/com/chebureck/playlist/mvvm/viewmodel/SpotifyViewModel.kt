package com.chebureck.playlist.mvvm.viewmodel

import android.app.Activity
import androidx.lifecycle.*
import com.chebureck.playlist.db.PlaylistWithTracks
import com.chebureck.playlist.mvvm.repository.PlaylistRepository
import com.chebureck.playlist.network.api.spotify.SpotifyApiManager
import com.chebureck.playlist.network.api.spotify.SpotifyAuthManager
import com.chebureck.playlist.network.api.spotify.SpotifyTokenProvider
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.launch

class SpotifyViewModel(
    private val spotifyAuthManager: SpotifyAuthManager,
    private val tokenProvider: SpotifyTokenProvider,
    private val playlistRepository: PlaylistRepository
) : ViewModel() {
    private val tokenData = MutableLiveData<String?>(token)
    private val playlists = playlistRepository.getPlaylists().map {
        if (!it.spotifyLoadedSuccessfully) {
            token = null
        }
        it.playlists
    }

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
    fun getPlaylists(): LiveData<List<PlaylistWithTracks>> = playlists

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
        viewModelScope.launch {
            playlistRepository.requestPlaylists()
        }
    }

    private fun refreshToken(value: String?) {
        value?.let {
            playlistRepository.setSpotifyApiManager(SpotifyApiManager(it))
        }
        tokenData.value = value
    }
}