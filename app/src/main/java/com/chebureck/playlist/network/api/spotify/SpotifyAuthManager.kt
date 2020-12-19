package com.chebureck.playlist.network.api.spotify

import android.app.Activity
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

class SpotifyAuthManager(private val clientId: String) {
    fun login(activityLoginListener: Activity, showDialog: Boolean) {
        val builder = AuthorizationRequest.Builder(
            clientId,
            AuthorizationResponse.Type.TOKEN,
            REDIRECT_URI
        )

        builder.setScopes(arrayOf("playlist-read-private"))
        builder.setShowDialog(showDialog)

        val request = builder.build()

        AuthorizationClient.openLoginActivity(
            activityLoginListener,
            REQUEST_CODE,
            request
        )
    }

    companion object {
        const val REQUEST_CODE = 1337
        const val REDIRECT_URI = "https://www.google.ru/"
    }
}
