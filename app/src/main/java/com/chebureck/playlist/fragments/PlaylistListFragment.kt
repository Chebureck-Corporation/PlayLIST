package com.chebureck.playlist.fragments

import android.net.sip.SipSession
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chebureck.playlist.R
import com.chebureck.playlist.adapters.PlaylistAdapter
import com.chebureck.playlist.db.Playlist
import com.chebureck.playlist.ui.presenter.MainActivityPresenter
import com.chebureck.playlist.ui.view.MainActivity

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image: ImageView = view.findViewById(R.id.icon)
        val headerTV: TextView = view.findViewById(R.id.tv_header)
        val plusButton: ImageButton = view.findViewById(R.id.btn_add)
        val andButton: Button = view.findViewById(R.id.btn_and)
        val orButton: Button = view.findViewById(R.id.btn_or)
        val xorButton: Button = view.findViewById(R.id.btn_xor)

        val state: String? = arguments!!.getString(KEY_STRING)
        when (state) {
            State.VIEWING.name -> {
                headerTV.visibility = View.GONE
                andButton.visibility = View.GONE
                orButton.visibility = View.GONE
                xorButton.visibility = View.GONE

                plusButton.setOnClickListener {
                    val mainActivity = activity as MainActivity
                    mainActivity.replaceRootFragmentByFragmentBackStack(PlaylistListFragment(State.ADDING), null)
                }
            }
            State.ADDING.name -> {
                image.visibility = View.INVISIBLE
                plusButton.visibility = View.GONE
                andButton.visibility = View.GONE
                xorButton.visibility = View.GONE

                orButton.setText(R.string.create)
                orButton.setOnClickListener{
                    val mainActivity = activity as MainActivity
                    mainActivity.replaceRootFragmentByFragmentBackStack(PlaylistListFragment(State.PROCESSING), null)
                }
            }
            State.PROCESSING.name -> {
                image.visibility = View.INVISIBLE
                headerTV.visibility = View.GONE
                plusButton.visibility = View.GONE
            }
        }

        val recycler: RecyclerView = view.findViewById(R.id.recycler)
        val adapter = PlaylistAdapter(playlists)

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
            ADDING, VIEWING, PROCESSING
        }
    }
}