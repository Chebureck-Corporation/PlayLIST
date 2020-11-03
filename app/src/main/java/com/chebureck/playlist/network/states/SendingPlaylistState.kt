package com.chebureck.playlist.network.states

sealed class SendingPlaylistState

object PlaylistSentState : SendingPlaylistState()

data class ErrorPlaylistSendingState(
    val error: Throwable?
) : SendingPlaylistState()
