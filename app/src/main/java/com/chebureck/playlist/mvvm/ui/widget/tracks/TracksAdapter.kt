package com.chebureck.playlist.mvvm.ui.widget.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.db.Track

class TracksAdapter(
    private val trackClickListener: TrackClickListener
) : RecyclerView.Adapter<TrackViewHolder>(), TrackViewHolder.TrackClickListener {

    interface TrackClickListener : TrackViewHolder.TrackClickListener

    var tracks: List<TracksAdapterItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.track, parent, false)

        return TrackViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount() = tracks.size

    override fun onTrackClicked(position: Int) {
        trackClickListener.onTrackClicked(position)
    }

    override fun onTrackLongClicked(position: Int, selected: Boolean) {
        trackClickListener.onTrackLongClicked(position, selected)
    }
}

data class TracksAdapterItem(
    val track: Track,
    var selected: Boolean
)
