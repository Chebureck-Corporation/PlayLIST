package com.chebureck.playlist.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.adapters.PlaylistAdapter
import com.chebureck.playlist.db.Playlist
import com.chebureck.playlist.viewholders.PlaylistViewHolder


class PlaylistListFragment private constructor(): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_playlists, container, false
        )
    }

    interface PlayListListener {
        fun onExitPressed()
        fun onButtonPressed(state: State)
        fun onItemClicked(id : String)
    }

    private var listener: PlayListListener? = null
    fun setListener(listener: PlayListListener?) {
        this.listener = listener
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image: ImageView = view.findViewById(R.id.icon)
        val headerTV: EditText = view.findViewById(R.id.tv_header)
        val plusButton: ImageButton = view.findViewById(R.id.btn_add)
        val andButton: Button = view.findViewById(R.id.btn_and)
        val orButton: Button = view.findViewById(R.id.btn_or)
        val xorButton: Button = view.findViewById(R.id.btn_xor)

        val recycler: RecyclerView = view.findViewById(R.id.recycler)
        val adapter = PlaylistAdapter(playlists, ItemClickHandler())

        when (arguments!!.getString(KEY_STRING)) {
            State.VIEWING.name -> {
                headerTV.visibility = View.GONE
                andButton.visibility = View.GONE
                orButton.setText(R.string.exit)
                orButton.setOnClickListener{
                    Log.i("exit", "pressed")
                    listener?.onExitPressed()

                }
                xorButton.visibility = View.GONE
                val animation: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.animator_button)
                plusButton.startAnimation(animation)
                plusButton.setOnClickListener {
                    listener?.onButtonPressed(State.CREATING)
                }

            }
            State.CREATING.name -> {
                image.visibility = View.INVISIBLE
                plusButton.visibility = View.GONE
            }
        }

        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.columns)
        )

    }

    companion object {
        var playlists: List<Playlist> = listOf()
        val KEY_STRING: String = "state"

        operator fun invoke(state: State) : PlaylistListFragment{
            val fragment = PlaylistListFragment()
            val args = Bundle()
            args.putString(KEY_STRING, state.name)
            fragment.arguments = args
            return fragment
        }

        enum class State{
            VIEWING, CREATING
        }
    }

    inner class ItemClickHandler: PlaylistViewHolder.IListener {
        override fun onItemClicked(position: Int) {
            val string  = playlists[position].name
            Log.i("onItemClicked", string)
            listener?.onItemClicked(string)
        }
    }
}