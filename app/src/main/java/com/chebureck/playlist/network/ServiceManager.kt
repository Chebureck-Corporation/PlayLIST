package com.chebureck.playlist.network

import androidx.annotation.IntDef
import com.chebureck.playlist.db.Playlist

interface ServiceManager {
    fun getPlaylists(playlistsGetListener: PlaylistsGetListener)

    fun sendPlaylist(playlist: Playlist, sendListener: PlaylistSendListener?)

    @ServiceType
    fun getServiceType(): Int
}

interface PlaylistsGetListener {
    fun onReceivePlaylistsSuccess(
        playlists: List<Playlist>,
        handler: ServiceManager,
        @GetPlaylistsResult result: Int
    )
}

interface PlaylistSendListener {
    fun onSendPlaylistResult(@SendPlaylistResult result: Int)
}

@IntDef(
    GetPlaylistsResult.OK,
    GetPlaylistsResult.UNKNOWN_ERROR
)
@Retention(AnnotationRetention.SOURCE)
annotation class GetPlaylistsResult {
    companion object GetPlaylistsResult {
        const val OK = 0
        const val UNKNOWN_ERROR = 1
    }
}

@IntDef(
    SendPlaylistResult.OK,
    SendPlaylistResult.UNKNOWN_ERROR
)
@Retention(AnnotationRetention.SOURCE)
annotation class SendPlaylistResult {
    companion object SendPlaylistResult {
        const val OK = 0
        const val UNKNOWN_ERROR = 1
    }
}
