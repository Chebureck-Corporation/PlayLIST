package com.chebureck.playlist.db

interface Playlist {
    val name: String

    val id: Long

    fun deleteTrack(trackId: Int)

    fun addTrack(trackId: Int)
}
