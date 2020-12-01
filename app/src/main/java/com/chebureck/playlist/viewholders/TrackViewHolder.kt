package com.chebureck.playlist.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R

class TrackViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    var authorName: TextView = itemView.findViewById(R.id.text_author)
    var trackName: TextView = itemView.findViewById(R.id.text_song_name)

    fun listen(event: (position: Int) -> Unit): TrackViewHolder {
        itemView.setOnClickListener {
            event.invoke(adapterPosition)
        }
        return this
    }

    fun bind(author: String, track: String) {
        authorName.text = author
        trackName.text = track
    }
}