package com.chebureck.playlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.db.Playlist
import com.chebureck.playlist.viewholders.PlaylistViewHolder

class PlaylistAdapter(
    playlistsList: List<Playlist>,
    iListener: PlaylistViewHolder.IListener
) : RecyclerView.Adapter<PlaylistViewHolder>() {
    private val playlists = playlistsList
    private val listener = iListener

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout: View = inflater.inflate(R.layout.item_playlist, parent, false)

        return PlaylistViewHolder(layout, listener)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position].name)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }
}
