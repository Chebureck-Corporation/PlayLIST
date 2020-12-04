package com.chebureck.playlist.db

import androidx.lifecycle.MutableLiveData

data class PlaylistWrapper(
    private val playlistEntity: PlaylistEntity
) : Playlist {
    lateinit var tracks: MutableLiveData<MutableList<TrackWrapper>>

    override val name: String
        get() = playlistEntity.name

    override val id: String
        get() = playlistEntity.id
    override val imageUrl: String?
        get() = null
}
