package com.chebureck.playlist.mvvm.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.db.Track
import com.chebureck.playlist.mvvm.ui.widget.tracks.TracksAdapter

class TracksFragment : Fragment(R.layout.fragment_tracks_screen) {
    private val tracksAdapter = TracksAdapter()
    private lateinit var tracks: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        @Suppress("UNCHECKED_CAST")
        val tracks = arguments?.getParcelableArray(BUNDLE_NAME_TRACKS) as Array<Track>
        tracksAdapter.tracks = tracks.toList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tracks = view.findViewById(R.id.recycler_tracks)
        tracks.layoutManager = LinearLayoutManager(context)
        tracks.adapter = tracksAdapter
    }

    companion object {
        const val BUNDLE_NAME_TRACKS = "tracks"
    }
}
