package com.chebureck.playlist.network.states

import com.chebureck.playlist.db.Playlists

sealed class ReceivingPlaylistsState

data class PlaylistsReadyState(
    val playlists: List<Playlists>
) : ReceivingPlaylistsState()

data class ReceivePlaylistsErrorState(
    val error: Throwable?
) : ReceivingPlaylistsState()
