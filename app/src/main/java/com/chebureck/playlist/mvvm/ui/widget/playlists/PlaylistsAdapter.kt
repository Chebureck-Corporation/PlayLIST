package com.chebureck.playlist.mvvm.ui.widget.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.db.Playlist

class PlaylistsAdapter(
    private val listener: PlaylistAdapterClickListener,
    private val isInCreatingFragment: Boolean = false
) : RecyclerView.Adapter<PlaylistViewHolder>(), PlaylistViewHolder.PlaylistClickListener {

    interface PlaylistAdapterClickListener : PlaylistViewHolder.PlaylistClickListener

    var playlists: List<Playlist> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var selectedList: List<Boolean>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout: View = inflater.inflate(R.layout.item_playlist, parent, false)

        return PlaylistViewHolder(layout, this, isInCreatingFragment)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position], selectedList?.get(position))
    }


    override fun getItemCount() = playlists.size

    override fun onPlaylistClicked(position: Int) {
        listener.onPlaylistClicked(position)
    }

    override fun onPlaylistLongClicked(position: Int, selected: Boolean) {
        listener.onPlaylistLongClicked(position, selected)
    }
}
