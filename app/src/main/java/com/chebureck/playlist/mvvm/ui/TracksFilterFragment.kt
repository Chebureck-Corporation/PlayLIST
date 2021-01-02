package com.chebureck.playlist.mvvm.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chebureck.playlist.R
import com.chebureck.playlist.databinding.FragmentTracksFilterBinding
import com.chebureck.playlist.mvvm.ui.widget.tracks.TracksAdapter
import com.chebureck.playlist.mvvm.ui.widget.tracks.TracksAdapterItem
import com.chebureck.playlist.mvvm.viewmodel.PlaylistCreateViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TracksFilterFragment :
    Fragment(R.layout.fragment_tracks_filter),
    TracksAdapter.TrackClickListener {

    private val playlistCreateViewModel
    by sharedViewModel<PlaylistCreateViewModel>()

    private val tracksAdapter = TracksAdapter(this)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        playlistCreateViewModel.getSelectedPlaylist().observe(viewLifecycleOwner) { playlist ->
            val resultTracks = playlist?.tracks ?: listOf()
            tracksAdapter.tracks = resultTracks.map { TracksAdapterItem(it.track, it.selected) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewBinding = FragmentTracksFilterBinding.bind(view)

        val tracks = viewBinding.recyclerTracks
        tracks.layoutManager = LinearLayoutManager(context)
        tracks.adapter = tracksAdapter
    }

    override fun onTrackClicked(position: Int) {
    }

    override fun onTrackLongClicked(position: Int, selected: Boolean) {
        playlistCreateViewModel.getSelectedPlaylist().value?.tracks?.get(position)?.let {
            it.selected = selected
        }
    }
}
