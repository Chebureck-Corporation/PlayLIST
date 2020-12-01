package com.chebureck.playlist.ui.presenter

import android.app.Activity
import android.content.Intent
import com.chebureck.playlist.network.api.spotify.SpotifyApiManager
import com.chebureck.playlist.network.api.spotify.SpotifyAuthManager
import com.chebureck.playlist.ui.repository.SpotifyRepository
import com.chebureck.playlist.ui.view.MainActivity
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationResponse

class MainActivityPresenter(
    mainActivity: MainActivity
) {
    private var spotifyApiManager: SpotifyApiManager? = null
    private val spotifyAuthManager = SpotifyAuthManager(
        mainActivity as Activity,
        SPOTIFY_CLIENT_ID
    )
    private val spotifyRepository = SpotifyRepository()

    fun onCreate() {
        auth()
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

    companion object {
        const val SPOTIFY_CLIENT_ID = "d38b258e60eb46ae9b2b03cde1fd8329"
    }
}
