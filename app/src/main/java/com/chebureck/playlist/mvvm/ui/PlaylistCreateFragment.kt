package com.chebureck.playlist.mvvm.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.db.PlaylistWithTracks
import com.chebureck.playlist.mvvm.ui.widget.playlists.PlaylistsAdapter
import com.chebureck.playlist.mvvm.viewmodel.SpotifyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistCreateFragment : Fragment(R.layout.fragment_playlist_create),
    PlaylistsAdapter.PlaylistAdapterClickListener {

    private val spotifyViewModel by viewModel<SpotifyViewModel>()

    private val playlistsAdapter = PlaylistsAdapter(this)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        @Suppress("UNCHECKED_CAST")
        val playlists =
            arguments?.getParcelableArray(BUNDLE_NAME_PLAYLISTS) as Array<PlaylistWithTracks>
        playlistsAdapter.playlists = playlists.toList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playlistsView = view.findViewById<RecyclerView>(R.id.playlists)

        playlistsView.adapter = playlistsAdapter
        playlistsView.layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.playlists_columns)
        )
    }

    override fun onPlaylistClicked(playlist: PlaylistWithTracks) {
        // TODO: delete it
        spotifyViewModel.savePlaylist(playlist)
    }

    companion object {
        const val BUNDLE_NAME_PLAYLISTS = "playlists"
    }
}
