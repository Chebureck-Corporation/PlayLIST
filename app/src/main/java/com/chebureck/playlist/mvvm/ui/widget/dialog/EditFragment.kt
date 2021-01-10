package com.chebureck.playlist.mvvm.ui.widget.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.chebureck.playlist.R
import com.chebureck.playlist.mvvm.viewmodel.SelectedPlaylistViewModel
import com.chebureck.playlist.mvvm.viewmodel.SpotifyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class EditFragment : DialogFragment() {

    private val selectedPlaylistViewModel
    by sharedViewModel<SelectedPlaylistViewModel>()
    private val spotifyViewModel by sharedViewModel<SpotifyViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = requireParentFragment().requireView().findNavController()

        val editText = view.findViewById<EditText>(R.id.edit_name)
        editText.setText(selectedPlaylistViewModel.getSelectedPlaylist().value?.name)

        val editBtn = view.findViewById<Button>(R.id.edit_button)
        editBtn.setOnClickListener {
            if (editText.text.toString() != "") {
                selectedPlaylistViewModel.getSelectedPlaylist().value?.let {
                    spotifyViewModel.updatePlaylistName(it, editText.text.toString())
                }
            }
            val action = EditFragmentDirections.actionEditFragmentToPlaylistsFragment()
            navController.navigate(action)
        }
    }
}
