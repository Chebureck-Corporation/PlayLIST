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

        navController = requireParentFragment().requireView().findNavController()

        val load = view.findViewById<Button>(R.id.load_button)
        if (selectedPlaylistViewModel.getSelectedPlaylist().value?.spotifyId == null) {
            load?.setOnClickListener {
                selectedPlaylistViewModel.getSelectedPlaylist().value?.let { playlist ->
                    spotifyViewModel.createSpotifyPlaylist(playlist)
                }
                val action = OptionsFragmentDirections.actionOptionsFragmentToPlaylistsFragment()
                navController.navigate(action)
            }
        } else{
            (load.parent as ViewGroup).removeView(load)
        }

        val edit = view.findViewById<Button>(R.id.edit_button)
        edit.setOnClickListener {
            val action = OptionsFragmentDirections.actionOptionsFragmentToEditFragment()
            navController.navigate(action)
        }

        val delete = view.findViewById<Button>(R.id.delete_button)
        delete.setOnClickListener{
            selectedPlaylistViewModel.getSelectedPlaylist().value?.let { playlist ->
                spotifyViewModel.unfollowPlaylist(playlist)
            }
            val action = OptionsFragmentDirections.actionOptionsFragmentToPlaylistsFragment()
            navController.navigate(action)
        }
    }
}