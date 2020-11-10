package com.chebureck.playlist.db

interface Playlist {
    var tracks: List<Track>

    var name: String

    val id: Int

    fun deleteTrack(trackId: Int)

    fun addTrack(trackId: Int)
}
