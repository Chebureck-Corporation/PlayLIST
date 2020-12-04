package com.chebureck.playlist.viewholders

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chebureck.playlist.R

class PlaylistViewHolder(
    itemView: View,
    iListener: IListener
) : RecyclerView.ViewHolder(itemView) {
    private val context: Context = itemView.context.applicationContext
    private var playlistName: TextView = itemView.findViewById(R.id.tv_name)
    private val playlistImage = itemView.findViewById<ImageView>(R.id.iv_playlist)

    interface IListener {
        fun onItemClicked(position: Int)
    }

    init {
        itemView.setOnLongClickListener {
            Toast.makeText(context, "Long clicked", Toast.LENGTH_SHORT).show()
            true
        }

        val listener = iListener

        itemView.setOnClickListener {
            listener.onItemClicked(adapterPosition)
        }
    }

    fun bind(name: String, imageUri: String?) {
        playlistName.text = name
        imageUri?.let {
            Glide.with(playlistImage).load(it).into(playlistImage)
        }
    }
}
