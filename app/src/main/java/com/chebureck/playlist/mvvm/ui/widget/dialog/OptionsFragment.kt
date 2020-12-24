package com.chebureck.playlist.mvvm.ui.widget.dialog

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.chebureck.playlist.R
import com.chebureck.playlist.mvvm.viewmodel.SelectedPlaylistViewModel
import com.chebureck.playlist.mvvm.viewmodel.SpotifyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class OptionsFragment : DialogFragment() {

    private val selectedPlaylistViewModel
            by sharedViewModel<SelectedPlaylistViewModel>()
    private val spotifyViewModel by sharedViewModel<SpotifyViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setGravity(Gravity.TOP + Gravity.END)
        return inflater.inflate(R.layout.options_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO setting navController

        val load = view.findViewById<Button>(R.id.load_button)
        load.setOnClickListener {
            selectedPlaylistViewModel.getSelectedPlaylist().value?.let { playlist ->
                spotifyViewModel.createSpotifyPlaylist(playlist)
            }
        }

        val edit = view.findViewById<Button>(R.id.edit_button)
        edit.setOnClickListener {
            // TODO navigation
        }

        val delete = view.findViewById<Button>(R.id.delete_button)
        delete.setOnClickListener{
            //TODO deleting of playlist
        }
    }
}