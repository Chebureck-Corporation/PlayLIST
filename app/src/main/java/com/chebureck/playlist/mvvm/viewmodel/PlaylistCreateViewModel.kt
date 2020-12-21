package com.chebureck.playlist.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chebureck.playlist.db.Playlist
import com.chebureck.playlist.db.PlaylistWithTracks
import com.chebureck.playlist.db.Track
import com.chebureck.playlist.mvvm.repository.PlaylistRepository
import com.chebureck.playlist.utils.ListsUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistCreateViewModel(
    private val playlistRepository: PlaylistRepository
) : ViewModel() {
    private val playlistsLiveData = MutableLiveData<List<PlaylistCreateData>>()
    private val generatedPlaylist = MutableLiveData<Event<PlaylistCreateData>>()

    fun getPlaylists(): LiveData<List<PlaylistCreateData>> = playlistsLiveData
    fun getCreatedPlaylist(): LiveData<Event<PlaylistCreateData>> = generatedPlaylist

    fun initPlaylists(playlists: List<PlaylistWithTracks>) {
        playlistsLiveData.value = playlists.map {
            PlaylistCreateData(
                it.imageUrl,
                it.tracks,
                it.name
            )
        }
    }

    fun andSelected() {
        val newTracks = ListsUtils.and(getSelectedPlaylists()) { it.filteredTracks }
        val newPlaylistCreatingData = createNewPlaylistRAM(newTracks)
        saveCreatedPlaylist(newPlaylistCreatingData)
    }

    fun orSelected() {
        val newTracks = ListsUtils.or(getSelectedPlaylists()) { it.filteredTracks }
        val newPlaylistCreatingData = createNewPlaylistRAM(newTracks)
        saveCreatedPlaylist(newPlaylistCreatingData)
    }

    fun xorSelected() {
        val newTracks = ListsUtils.xor(getSelectedPlaylists()) { it.filteredTracks }
        val newPlaylistCreatingData = createNewPlaylistRAM(newTracks)
        saveCreatedPlaylist(newPlaylistCreatingData)
    }

    private fun getSelectedPlaylists() = playlistsLiveData.value!!.filter {
        it.selected
    }

    private fun createNewPlaylistRAM(tracks: List<Track>) = PlaylistCreateData(
        null,
        tracks,
        "Temp"
    )

    private fun saveCreatedPlaylist(createdPlaylist: PlaylistCreateData) {
        viewModelScope.launch {
            playlistRepository.saveLocalPlaylist(
                PlaylistWithTracks(
                    Playlist(null, createdPlaylist.imageUrl, createdPlaylist.name),
                    createdPlaylist.filteredTracks
                )
            )
            generatedPlaylist.postValue(Event(createdPlaylist))
        }
    }

    fun setSelected(position: Int, selected: Boolean) {
        playlistsLiveData.value?.get(position)?.let { it.selected = selected }
    }
}

data class PlaylistCreateData(
    val imageUrl: String?,
    val originalTracks: List<Track>,
    val name: String,
    var selected: Boolean = false
) {
    val filteredTracks = originalTracks
}