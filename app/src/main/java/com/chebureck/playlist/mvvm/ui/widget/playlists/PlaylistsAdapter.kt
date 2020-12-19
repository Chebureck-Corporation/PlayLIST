package com.chebureck.playlist.mvvm.ui.widget.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.db.PlaylistWithTracks

class PlaylistsAdapter(
    private val listener: PlaylistAdapterClickListener
) : RecyclerView.Adapter<PlaylistViewHolder>(), PlaylistViewHolder.PlaylistClickListener {

    interface PlaylistAdapterClickListener {
        fun onPlaylistClicked(playlist: PlaylistWithTracks)
    }

    var playlists: List<PlaylistWithTracks> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout: View = inflater.inflate(R.layout.item_playlist, parent, false)

        return PlaylistViewHolder(layout, this)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position])
    }

    override fun getItemCount() = playlists.size

    override fun onPlaylistClicked(position: Int) {
        listener.onPlaylistClicked(playlists[position])
    }
}
