package com.chebureck.playlist.network.states

import com.chebureck.playlist.db.Playlist

sealed class ReceivingPlaylistsState

data class PlaylistsReadyState(
    val playlists: List<Playlist>
) : ReceivingPlaylistsState()

data class ReceivePlaylistsErrorState(
    val error: Throwable?
) : ReceivingPlaylistsState()
