package com.chebureck.playlist.fragments

import android.content.Context
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
import com.chebureck.playlist.ui.view.MainActivity
import com.chebureck.playlist.viewholders.PlaylistViewHolder

class PlaylistCreateFragment : Fragment() {
    private var playlists: List<Playlist> = listOf()

    interface PlaylistCreateListener {
        fun onItemClicked(playlistName: String)
    }

    private var listener: PlaylistCreateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playlists = arguments?.getParcelableArray(PLAYLISTS_ARGUMENT_ID)?.map {
            it as Playlist
        } ?: listOf()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (requireActivity() as MainActivity).findListener()
    }

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

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    inner class ItemClickHandler : PlaylistViewHolder.IListener {
        override fun onItemClicked(position: Int) {
            val string = playlists[position].name
            Log.i("onItemClicked", string)
            listener?.onItemClicked(string)
        }
    }

    companion object {
        private const val PLAYLISTS_ARGUMENT_ID = "PLAYLISTS_ARGUMENT_ID"

        fun createInstance(playlists: List<Playlist>) =
            PlaylistCreateFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray(PLAYLISTS_ARGUMENT_ID, playlists.toTypedArray())
                }
            }
    }
}
