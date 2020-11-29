package com.chebureck.playlist.viewholders

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.view.DragEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R


class PlaylistViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnLongClickListener {
            val cliptext = "This is our ClipData text"
            val item = ClipData.Item(cliptext)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(cliptext, mimeTypes, item)

            val dragShadowBuilder = DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, item, 0)

            it.visibility = View.INVISIBLE
            true
        }
    }

    private var playlistName: TextView = itemView.findViewById(R.id.tv_name)

    fun bind(name: String) {
        playlistName.text = name
    }
}