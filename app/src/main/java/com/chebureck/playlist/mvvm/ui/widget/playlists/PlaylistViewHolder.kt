package com.chebureck.playlist.mvvm.ui.widget.playlists

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chebureck.playlist.R
import com.chebureck.playlist.db.Playlist

class PlaylistViewHolder(
    itemView: View,
    playlistClickListener: PlaylistClickListener
) : RecyclerView.ViewHolder(itemView) {
    private var playlistName = itemView.findViewById<TextView>(R.id.tv_name)
    private val playlistImage = itemView.findViewById<ImageView>(R.id.iv_playlist)
    private var selected = false

    interface PlaylistClickListener {
        fun onPlaylistClicked(position: Int)
        fun onPlaylistLongClicked(position: Int, selected: Boolean)
    }

    init {
        itemView.setOnClickListener {
            playlistClickListener.onPlaylistClicked(adapterPosition)
        }
        itemView.setOnLongClickListener {
            selected = !selected
            val newColor = if (selected) {
                Color.GREEN
            } else {
                Color.TRANSPARENT
            }
            itemView.setBackgroundColor(newColor)
            playlistClickListener.onPlaylistLongClicked(adapterPosition, selected)
            true
        }
    }

    fun bind(playlist: Playlist) {
        playlistName.text = playlist.name
        playlist.imageUrl?.let {
            Glide.with(playlistImage).load(it).into(playlistImage)
        } ?: playlistImage.setImageDrawable(null)
    }
}
