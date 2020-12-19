package com.chebureck.playlist.mvvm.ui.widget.tracks

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.db.Track

class TrackViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    var authorName = itemView.findViewById<TextView>(R.id.text_author)
    var trackName = itemView.findViewById<TextView>(R.id.text_song_name)

    fun bind(track: Track) {
        authorName.text = track.author
        trackName.text = track.name
    }
}
