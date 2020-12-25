package com.chebureck.playlist.mvvm.viewmodel

import android.app.Activity
import androidx.lifecycle.*
import com.chebureck.playlist.db.PlaylistWithTracks
import com.chebureck.playlist.mvvm.repository.PlaylistRepository
import com.chebureck.playlist.network.api.spotify.SpotifyAuthManager
import com.chebureck.playlist.network.api.spotify.SpotifyTokenProvider
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

@KoinApiExtension
class SpotifyViewModel(
    private val spotifyAuthManager: SpotifyAuthManager,
    private val tokenProvider: SpotifyTokenProvider,
    private val playlistRepository: PlaylistRepository
) : ViewModel(), KoinComponent {
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

    // TODO: it must be used to create playlist
    fun createSpotifyPlaylist(playlist: PlaylistWithTracks) {
        viewModelScope.launch {
            playlistRepository.createSpotifyPlaylist(playlist)
        }
    }

    fun updatePlaylistName(playlist: PlaylistWithTracks) {
        viewModelScope.launch {
            playlistRepository.updatePlaylistName(playlist)
        }
    }

    fun unfollowPlaylist(playlist: PlaylistWithTracks) {
        viewModelScope.launch {
            playlistRepository.unfollowPlaylist(playlist)
        }
    }

    private fun refreshToken(newToken: String?) {
        newToken?.let {
            playlistRepository.setSpotifyApiManager(get { parametersOf(newToken) })
        }
        tokenData.value = newToken
    }
}