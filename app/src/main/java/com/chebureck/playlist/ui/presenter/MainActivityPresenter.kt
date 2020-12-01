package com.chebureck.playlist.ui.presenter

import android.app.Activity
import android.content.Intent
import com.chebureck.playlist.fragments.AuthFragment
import com.chebureck.playlist.fragments.PlaylistListFragment
import com.chebureck.playlist.network.api.spotify.SpotifyApiManager
import com.chebureck.playlist.network.api.spotify.SpotifyAuthManager
import com.chebureck.playlist.ui.repository.SpotifyRepository
import com.chebureck.playlist.ui.view.MainActivity
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityPresenter(
    private val mainActivity: MainActivity
) : AuthFragment.AuthListener {
    private var spotifyApiManager: SpotifyApiManager? = null
    private val spotifyAuthManager = SpotifyAuthManager(
        mainActivity as Activity,
        SPOTIFY_CLIENT_ID
    )
    private val spotifyRepository = SpotifyRepository()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun onCreate() {
        mainActivity.replaceRootFragmentByFragment(AuthFragment().apply {
            setListener(this@MainActivityPresenter)
        })
    }

    private fun auth() {
        spotifyAuthManager.auth()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == SpotifyAuthManager.REQUEST_CODE) {
            val response = AuthenticationClient
                .getResponse(resultCode, intent)
            when (response.type) {
                AuthenticationResponse.Type.TOKEN -> {
                    spotifyRepository.token = response.accessToken
                    spotifyApiManager = SpotifyApiManager(response.accessToken)
                    onSuccessfulAuth()
                }
                AuthenticationResponse.Type.ERROR -> {
                    // ON ERROR
                }
                else -> {
                    // ELSE
                }
            }
        }
    }

    private fun onSuccessfulAuth() {
        ioScope.launch {
            val id = spotifyApiManager?.getMe()?.id ?: ""
            PlaylistListFragment.playlists = spotifyApiManager?.getPlaylists(id) ?: listOf()
            launch(Dispatchers.Main.immediate) {
                mainActivity.replaceRootFragmentByFragment(PlaylistListFragment())
            }
        }
    }

    companion object {
        const val SPOTIFY_CLIENT_ID = "d38b258e60eb46ae9b2b03cde1fd8329"
    }

    override fun onSignInPressed() {
        auth()
    }
}
