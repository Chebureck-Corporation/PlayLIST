package com.chebureck.playlist.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.adapters.PlaylistAdapter
import com.chebureck.playlist.db.Playlist
import com.chebureck.playlist.viewholders.PlaylistViewHolder

class PlaylistListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_playlists,
            container,
            false
        )
    }

    interface PlayListListener {
        fun onExitPressed()
        fun onPlusButtonPressed()
        fun onItemClicked(playlistName: String)
    }

    private var listener: PlayListListener? = null
    fun setListener(listener: PlayListListener?) {
        this.listener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plusButton: ImageButton = view.findViewById(R.id.btn_add)
        val exitButton: Button = view.findViewById(R.id.btn_exit)

        val recycler: RecyclerView =
            view.findViewById(
                R.id.recycler
            )
        val adapter = PlaylistAdapter(
            playlists,
            ItemClickHandler()
        )
        exitButton.setText(R.string.exit)
        exitButton.setOnClickListener {
            Log.i("exit", "pressed")
            listener?.onExitPressed()
        }
        val animation: Animation = AnimationUtils
            .loadAnimation(
                requireContext(),
                R.anim.animator_button
            )
        plusButton.startAnimation(animation)
        plusButton.setOnClickListener {
            listener?.onPlusButtonPressed()
        }
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.columns)
        )
    }

    inner class ItemClickHandler : PlaylistViewHolder.IListener {
        override fun onItemClicked(position: Int) {
            val string = playlists[position].name
            Log.i("onItemClicked", string)
            listener?.onItemClicked(string)
        }
    }

    companion object {
        var playlists: List<Playlist> = listOf()
    }
}
