package com.chebureck.playlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.db.TrackWrapper
import com.chebureck.playlist.viewholders.TrackViewHolder

class TrackAdapter(
    trackList: List<TrackWrapper>
) : RecyclerView.Adapter<TrackViewHolder>() {
    private var tracks: List<TrackWrapper> = trackList

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.track, parent, false)
        val holder = TrackViewHolder(view)
        return holder.listen { pos ->
            tracks[pos]
        }
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(
            tracks[position].author,
            tracks[position].name
        )
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
}
