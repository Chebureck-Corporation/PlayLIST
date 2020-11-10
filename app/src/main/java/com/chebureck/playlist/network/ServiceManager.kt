package com.chebureck.playlist.network

import com.chebureck.playlist.db.Playlists
import com.chebureck.playlist.network.states.ReceivingPlaylistsState
import com.chebureck.playlist.network.states.SendingPlaylistState

interface ServiceManager {
    fun getPlaylists(playlistsListener: (state: ReceivingPlaylistsState) -> Unit)

    fun sendPlaylist(
        playlist: Playlists,
        sendingListener: ((state: SendingPlaylistState) -> Unit)?
    )

    @ServiceType
    fun getServiceType(): Int
}
