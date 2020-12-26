package com.chebureck.playlist.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chebureck.playlist.db.PlaylistWithTracks

class SelectedPlaylistViewModel : ViewModel() {
    private val selectedPlaylist = MutableLiveData<PlaylistWithTracks>()

    fun getSelectedPlaylist(): LiveData<PlaylistWithTracks> = selectedPlaylist

    fun initSelectedPlaylist(selectedPlaylist: PlaylistWithTracks) {
        this.selectedPlaylist.value = selectedPlaylist
    }
}