package com.chebureck.playlist.mvvm.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chebureck.playlist.R
import com.chebureck.playlist.databinding.FragmentPlaylistCreateBinding
import com.chebureck.playlist.db.Playlist
import com.chebureck.playlist.mvvm.ui.widget.playlists.PlaylistsAdapter
import com.chebureck.playlist.mvvm.viewmodel.PlaylistCreateViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PlaylistCreateFragment :
    Fragment(R.layout.fragment_playlist_create),
    PlaylistsAdapter.PlaylistAdapterClickListener {

    private val playlistCreateViewModel
    by sharedViewModel<PlaylistCreateViewModel>()

    private val playlistsAdapter = PlaylistsAdapter(this)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        playlistCreateViewModel.getPlaylists().observe(viewLifecycleOwner) { newPlaylists ->
            playlistsAdapter.playlists = newPlaylists.map {
                Playlist(null, it.imageUrl, it.name)
            }
        }
        playlistCreateViewModel.getCreatedPlaylist().observe(viewLifecycleOwner) {
            if (it.getContentIfNotHandledOrReturnNull() != null) {
                val action = PlaylistCreateFragmentDirections.actionPlaylistCreated()
                findNavController().navigate(action)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        playlistCreateViewModel.setSelectedPlaylist(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPlaylistCreateBinding.bind(view)

        val playlistsView = binding.playlists
        playlistsView.adapter = playlistsAdapter
        playlistsView.layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.playlists_columns)
        )

        val editText = view.findViewById<EditText>(R.id.et_header)

        val btnAnd = binding.btnAnd
        btnAnd.setOnClickListener {
            playlistCreateViewModel.andSelected(editText.text.toString())
        }

        val btnOr = binding.btnOr
        btnOr.setOnClickListener {
            playlistCreateViewModel.orSelected(editText.text.toString())
        }

        val btnXor = binding.btnXor
        btnXor.setOnClickListener {
            playlistCreateViewModel.xorSelected(editText.text.toString())
        }
    }

    override fun onPlaylistClicked(position: Int) {
        playlistCreateViewModel.setSelectedPlaylist(
            playlistCreateViewModel.getPlaylists().value?.get(
                position
            )
        )
        val action = PlaylistCreateFragmentDirections.actionPlaylistSelected()
        findNavController().navigate(action)
    }

    override fun onPlaylistLongClicked(position: Int, selected: Boolean) {
        playlistCreateViewModel.setSelectedPlaylist(position, selected)
    }
}
