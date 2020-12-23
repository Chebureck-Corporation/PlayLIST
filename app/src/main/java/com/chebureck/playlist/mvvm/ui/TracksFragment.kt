package com.chebureck.playlist.mvvm.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.databinding.FragmentTracksScreenBinding
import com.chebureck.playlist.mvvm.ui.widget.tracks.TracksAdapter
import com.chebureck.playlist.mvvm.ui.widget.tracks.TracksAdapterItem
import com.chebureck.playlist.mvvm.viewmodel.SelectedPlaylistViewModel
import com.chebureck.playlist.mvvm.viewmodel.SpotifyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class TracksFragment : Fragment(R.layout.fragment_tracks_screen), TracksAdapter.TrackClickListener {

    private val selectedPlaylistViewModel
            by sharedViewModel<SelectedPlaylistViewModel>()
    private val spotifyViewModel by sharedViewModel<SpotifyViewModel>()

    private val tracksAdapter = TracksAdapter(this)
    private lateinit var tracks: RecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        selectedPlaylistViewModel.getSelectedPlaylist().observe(viewLifecycleOwner) { playlist ->
            tracksAdapter.tracks = playlist.tracks.map { TracksAdapterItem(it, false) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTracksScreenBinding.bind(view)

        tracks = binding.recyclerTracks
        tracks.layoutManager = LinearLayoutManager(context)
        tracks.adapter = tracksAdapter

        val options = binding.btnOptions
        options.setOnClickListener {
            selectedPlaylistViewModel.getSelectedPlaylist().value?.let { playlist ->
                spotifyViewModel.createSpotifyPlaylist(playlist)
            }
        }
    }

    override fun onTrackClicked(position: Int) {

    }

    override fun onTrackLongClicked(position: Int, selected: Boolean) {

    }

    companion object {
        const val BUNDLE_NAME_TRACKS = "tracks"
    }
}
