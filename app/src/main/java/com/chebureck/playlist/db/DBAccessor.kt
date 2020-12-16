package com.chebureck.playlist.db

import com.chebureck.playlist.operations.Operations

class DBAccessor(
    private val operation: Operations,
    private val playlistDao: PlaylistDaoImpl
) {
    fun and(playlist: Playlist, tracksList: List<List<Track>>) {
        val result = operation.and(tracksList)
        playlistDao.addPlaylist(playlist)
        result.forEach { track ->
            playlistDao.trackToPlaylist(playlist.id, track.id)
        }
    }

    fun or(playlist: Playlist, tracksList: List<List<Track>>) {
        val result = operation.or(tracksList)
        playlistDao.addPlaylist(playlist)
        result.forEach { track ->
            playlistDao.trackToPlaylist(playlist.id, track.id)
        }
    }

    fun xor(playlist: Playlist, tracksList: List<List<Track>>) {
        val result = operation.xor(tracksList)
        playlistDao.addPlaylist(playlist)
        result.forEach { track ->
            playlistDao.trackToPlaylist(playlist.id, track.id)
        }
    }
}
