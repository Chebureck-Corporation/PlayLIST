package com.chebureck.playlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chebureck.playlist.mvvm.viewmodel.SpotifyViewModel
import com.chebureck.playlist.network.api.spotify.SpotifyAuthManager
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val spotifyViewModel: SpotifyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SpotifyAuthManager.REQUEST_CODE) {
            val response: AuthorizationResponse? = AuthorizationClient
                .getResponse(resultCode, data)
            spotifyViewModel.onLoginResponse(response)
        }
    }
}
