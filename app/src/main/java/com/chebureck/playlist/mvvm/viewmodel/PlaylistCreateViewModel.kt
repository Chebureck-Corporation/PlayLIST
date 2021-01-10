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
import kotlinx.coroutines.launch

class PlaylistCreateViewModel(
    private val playlistRepository: PlaylistRepository
) : ViewModel() {
    private val playlistsLiveData = MutableLiveData<List<PlaylistCreateData>>()
    private val generatedPlaylist = MutableLiveData<Event<PlaylistCreateData>>()
    private val selectedPlaylist = MutableLiveData<PlaylistCreateData>()

    fun getPlaylists(): LiveData<List<PlaylistCreateData>> = playlistsLiveData
    fun getCreatedPlaylist(): LiveData<Event<PlaylistCreateData>> = generatedPlaylist
    fun getSelectedPlaylist(): LiveData<PlaylistCreateData> = selectedPlaylist

    fun initPlaylists(playlists: List<PlaylistWithTracks>) {
        playlistsLiveData.value = playlists.map { playlist ->
            PlaylistCreateData(
                playlist.imageUrl,
                playlist.tracks.map { TrackCreateData(it) },
                playlist.name
            )
        }
    }

    fun setSelectedPlaylist(playlist: PlaylistCreateData?, position: Int?) {
        selectedPlaylist.value = playlist
    }

    fun andSelected(name: String?) {
        val newTracks = ListsUtils.and(getSelectedPlaylists()) { it.filteredTracks }
        var playlistName = "Temp"
        if (name != null) {
            playlistName = name
        }
        val newPlaylistCreatingData = createNewPlaylistRAM(newTracks, playlistName)
        saveCreatedPlaylist(newPlaylistCreatingData)
    }

    fun orSelected(name: String?) {
        val newTracks = ListsUtils.or(getSelectedPlaylists()) { it.filteredTracks }
        var playlistName = "Temp"
        if (name != null) {
            playlistName = name
        }
        val newPlaylistCreatingData = createNewPlaylistRAM(newTracks, playlistName)
        saveCreatedPlaylist(newPlaylistCreatingData)
    }

    fun xorSelected(name: String?) {
        val newTracks = ListsUtils.xor(getSelectedPlaylists()) { it.filteredTracks }
        var playlistName = "Temp"
        if (name != null) {
            playlistName = name
        }
        val newPlaylistCreatingData = createNewPlaylistRAM(newTracks, playlistName)
        saveCreatedPlaylist(newPlaylistCreatingData)
    }

    private fun getSelectedPlaylists() = playlistsLiveData.value!!.filter {
        it.selected
    }

    private fun createNewPlaylistRAM(tracks: List<TrackCreateData>, name: String) =
        PlaylistCreateData(
            null,
            tracks,
            name
        )

    private fun saveCreatedPlaylist(createdPlaylist: PlaylistCreateData) {
        viewModelScope.launch {
            playlistRepository.saveLocalPlaylist(
                PlaylistWithTracks(
                    Playlist(null, createdPlaylist.imageUrl, createdPlaylist.name),
                    createdPlaylist.filteredTracks.map { it.track }
                )
            )
            generatedPlaylist.postValue(Event(createdPlaylist))
        }
    }

    fun setSelectedPlaylist(position: Int, selected: Boolean) {
        playlistsLiveData.value?.get(position)?.let { it.selected = selected }
    }
}

data class PlaylistCreateData(
    val imageUrl: String?,
    val tracks: List<TrackCreateData>,
    val name: String,
    var selected: Boolean = false
) {
    val filteredTracks
        get() = tracks.filter { it.selected }
}

data class TrackCreateData(
    val track: Track,
) {
    var selected: Boolean = true
}
