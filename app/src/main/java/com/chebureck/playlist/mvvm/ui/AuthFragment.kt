package com.chebureck.playlist.mvvm.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.chebureck.playlist.R
import com.chebureck.playlist.mvvm.viewmodel.SpotifyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {
    private val spotifyViewModel by sharedViewModel<SpotifyViewModel>()
    private val loginObserver: Observer<String?> = Observer {
        if (it != null) {
            onSuccessLogin()
        }
    }

    private lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        spotifyViewModel.getToken().observe(viewLifecycleOwner, loginObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val authButton = view.findViewById<Button>(R.id.btn_sign)
        navController = view.findNavController()

        authButton.setOnClickListener {
            spotifyViewModel.requestLogin(activity as Activity)
        }
    }

    private fun onSuccessLogin() {
        val action = AuthFragmentDirections.actionSignedIn()
        navController.navigate(action)
    }
}
