package com.chebureck.playlist.mvvm.ui.widget.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
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
        dialog?.window?.setBackgroundDrawable(
            ColorDrawable(
                android.graphics.Color
                    .TRANSPARENT
            )
        )
        return inflater.inflate(R.layout.fragment_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = requireParentFragment().requireView().findNavController()

        val load = view.findViewById<Button>(R.id.load_button)
        val cloud = view.findViewById<ImageView>(R.id.cloud)
        if (selectedPlaylistViewModel.getSelectedPlaylist().value?.spotifyId == null) {
            load?.setOnClickListener {
                selectedPlaylistViewModel.getSelectedPlaylist().value?.let { playlist ->
                    spotifyViewModel.createSpotifyPlaylist(playlist)
                }
                val action = OptionsFragmentDirections.actionOptionsFragmentToPlaylistsFragment()
                navController.navigate(action)
            }
        } else {
            (load.parent as ViewGroup).removeView(load)
            (cloud.parent as ViewGroup).removeView(cloud)
        }

        val edit = view.findViewById<Button>(R.id.edit_button)
        edit.setOnClickListener {
            val action = OptionsFragmentDirections.actionOptionsFragmentToEditFragment()
            navController.navigate(action)
        }

        val delete = view.findViewById<Button>(R.id.delete_button)
        delete.setOnClickListener {
            selectedPlaylistViewModel.getSelectedPlaylist().value?.let { playlist ->
                spotifyViewModel.unfollowPlaylist(playlist)
            }
            val action = OptionsFragmentDirections.actionOptionsFragmentToPlaylistsFragment()
            navController.navigate(action)
        }
    }
}
