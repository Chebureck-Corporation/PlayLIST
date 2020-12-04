package com.chebureck.playlist.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.adapters.PlaylistAdapter
import com.chebureck.playlist.db.Playlist
import com.chebureck.playlist.viewholders.PlaylistViewHolder

class PlaylistCreateFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_playlist_create,
            container,
            false
        )
    }

    interface PlaylistCreateListener {
        fun onItemClicked(playlistName: String)
    }

    private var listener: PlaylistCreateListener? = null
    fun setListener(listener: PlaylistCreateListener?) {
        this.listener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler: RecyclerView =
            view.findViewById(
                R.id.recycler
            )
        val adapter = PlaylistAdapter(
            playlists,
            ItemClickHandler()
        )

        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.columns)
        )
    }

    inner class ItemClickHandler : PlaylistViewHolder.IListener {
        override fun onItemClicked(position: Int) {
            val string = playlists[position].name
            Log.i("onItemClicked", string)
            listener?.onItemClicked(string)
        }
    }

    companion object {
        var playlists: List<Playlist> = listOf()
    }
}
