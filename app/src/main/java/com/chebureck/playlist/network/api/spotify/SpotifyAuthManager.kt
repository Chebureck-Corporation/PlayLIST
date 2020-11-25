package com.chebureck.playlist.network.api.spotify

import android.app.Activity
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse

class SpotifyAuthManager(private val activityContext: Activity, private val clientId: String) {
    fun auth() {
        val builder = AuthenticationRequest.Builder(
            clientId,
            AuthenticationResponse.Type.TOKEN,
            REDIRECT_URI
        )

        builder.setScopes(arrayOf("playlist-read-private", "playlist-read-collaborative"))
        val request = builder.build()

        AuthenticationClient.openLoginActivity(
            activityContext, REQUEST_CODE,
            request
        )
    }

    companion object {
        const val REQUEST_CODE = 1337
        const val REDIRECT_URI = "https://www.google.ru/"
    }
}