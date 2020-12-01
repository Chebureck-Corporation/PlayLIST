package com.chebureck.playlist.viewholders

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R


class PlaylistViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    private val context: Context = itemView.context.applicationContext

    init {
        itemView.setOnLongClickListener {
            Toast.makeText(context, "Long clicked", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private var playlistName: TextView = itemView.findViewById(R.id.tv_name)

    fun bind(name: String) {
        playlistName.text = name
    }
}