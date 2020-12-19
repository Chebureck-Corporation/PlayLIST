package com.chebureck.playlist.mvvm.ui.widget.playlists

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chebureck.playlist.R
import com.chebureck.playlist.db.PlaylistWithTracks

class PlaylistViewHolder(
    itemView: View,
    playlistClickListener: PlaylistClickListener
) : RecyclerView.ViewHolder(itemView) {
    private var playlistName = itemView.findViewById<TextView>(R.id.tv_name)
    private val playlistImage = itemView.findViewById<ImageView>(R.id.iv_playlist)

    interface PlaylistClickListener {
        fun onPlaylistClicked(position: Int)
    }

    init {
        itemView.setOnClickListener {
            playlistClickListener.onPlaylistClicked(adapterPosition)
        }
    }

    fun bind(playlist: PlaylistWithTracks) {
        playlistName.text = playlist.name
        playlist.imageUrl?.let {
            Glide.with(playlistImage).load(it).into(playlistImage)
        }
    }
}
