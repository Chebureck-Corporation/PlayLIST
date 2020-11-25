package com.chebureck.playlist.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R

class TrackViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    lateinit var authorName : TextView
    lateinit var trackName : TextView

    init {
        authorName = this.itemView.findViewById(R.id.text_author)
        trackName = this.itemView.findViewById(R.id.text_song_name)
    }

    fun bind(){
        authorName.setText("")
        trackName.setText("")
    }
}