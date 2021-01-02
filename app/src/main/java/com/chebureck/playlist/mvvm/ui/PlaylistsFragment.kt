package com.chebureck.playlist.mvvm.ui

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.mvvm.ui.widget.playlists.PlaylistsAdapter
import com.chebureck.playlist.mvvm.viewmodel.PlaylistCreateViewModel
import com.chebureck.playlist.mvvm.viewmodel.SelectedPlaylistViewModel
import com.chebureck.playlist.mvvm.viewmodel.SpotifyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class PlaylistsFragment :
    Fragment(R.layout.fragment_playlists),
    PlaylistsAdapter.PlaylistAdapterClickListener {

    private val selectedPlaylistViewModel
    by sharedViewModel<SelectedPlaylistViewModel>()
    private val spotifyViewModel by sharedViewModel<SpotifyViewModel>()
    private val playlistCreateViewModel
    by sharedViewModel<PlaylistCreateViewModel>()
    private lateinit var navController: NavController

    private val playlistsAdapter = PlaylistsAdapter(this)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        spotifyViewModel.getToken().observe(viewLifecycleOwner) {
            if (it == null) {
                val action = PlaylistsFragmentDirections.actionAuth()
                navController.navigate(action)
            } else {
                spotifyViewModel.requestMyPlaylists()
            }
        }
        spotifyViewModel.getPlaylists().observe(viewLifecycleOwner) {
            if (it != null) {
                playlistsAdapter.playlists = it.map { it.playlist }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        val animation: Animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.animator_button
        )

        val plusButton = view.findViewById<ImageButton>(R.id.btn_add)
        val exitButton = view.findViewById<Button>(R.id.btn_exit)
        val playlists = view.findViewById<RecyclerView>(R.id.playlists)

        exitButton.setText(R.string.exit)

        plusButton.startAnimation(animation)
        plusButton.setOnClickListener {
            val currentPlaylists = spotifyViewModel.getPlaylists().value
                ?: return@setOnClickListener
            playlistCreateViewModel.initPlaylists(currentPlaylists)
            navController.navigate(R.id.playlistCreateFragment)
        }

        playlists.adapter = playlistsAdapter
        playlists.layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.playlists_columns)
        )
    }

    override fun onPlaylistLongClicked(position: Int, selected: Boolean) {
    }

    override fun onPlaylistClicked(position: Int) {
        val playlistWithTracks =
            spotifyViewModel.getPlaylists().value?.get(position)
                ?: throw Throwable("Unpredicted behavior")

        selectedPlaylistViewModel.initSelectedPlaylist(playlistWithTracks)

        val action = PlaylistsFragmentDirections.actionPlaylistTracks()
        navController.navigate(action)
    }
}
