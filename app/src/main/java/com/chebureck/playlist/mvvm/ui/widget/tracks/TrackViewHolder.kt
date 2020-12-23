package com.chebureck.playlist.mvvm.ui.widget.tracks

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R

class TrackViewHolder(
    itemView: View,
    trackClickListener: TrackClickListener
) : RecyclerView.ViewHolder(itemView) {

    interface TrackClickListener {
        fun onTrackClicked(position: Int)
        fun onTrackLongClicked(position: Int, selected: Boolean)
    }

    private var authorName = itemView.findViewById<TextView>(R.id.text_author)
    private var trackName = itemView.findViewById<TextView>(R.id.text_song_name)
    private var selected = false

    init {
        itemView.setOnClickListener {
            trackClickListener.onTrackClicked(adapterPosition)
        }
        itemView.setOnLongClickListener {
            selected = !selected
            updateColor()
            trackClickListener.onTrackLongClicked(adapterPosition, selected)
            true
        }
    }

    fun bind(tracksAdapterItem: TracksAdapterItem) {
        authorName.text = tracksAdapterItem.track.author
        trackName.text = tracksAdapterItem.track.name
        selected = tracksAdapterItem.selected
        updateColor()
    }

    private fun updateColor() {
        val newColor = if (selected) {
            Color.GREEN
        } else {
            Color.TRANSPARENT
        }
        itemView.setBackgroundColor(newColor)
    }
}
