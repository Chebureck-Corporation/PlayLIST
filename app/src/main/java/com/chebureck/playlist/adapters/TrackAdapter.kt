package com.chebureck.playlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.viewholders.TrackViewHolder

class TrackAdapter : RecyclerView.Adapter<TrackViewHolder>() {
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout: View = inflater.inflate(R.layout.track, parent, false)

        return TrackViewHolder(layout)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind();
    }

    override fun getItemCount(): Int {
        return 100
    }
}