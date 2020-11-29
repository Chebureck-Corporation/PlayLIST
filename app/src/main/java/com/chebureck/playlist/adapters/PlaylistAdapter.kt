package com.chebureck.playlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.db.PlaylistWrapper
import com.chebureck.playlist.viewholders.PlaylistViewHolder

class PlaylistAdapter(
    playlistsList: List<PlaylistWrapper>
) : RecyclerView.Adapter<PlaylistViewHolder>() {
    private val playlists = playlistsList

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout: View = inflater.inflate(R.layout.item_playlist, parent, false)

        return PlaylistViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position].name)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }
}