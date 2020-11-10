package com.chebureck.playlist.db

interface Playlists {
    var tracks: List<Tracks>

    var name: String

    val id: Int

    fun deleteTrack(trackId: Int)

    fun addTrack(trackId: Int)
}
