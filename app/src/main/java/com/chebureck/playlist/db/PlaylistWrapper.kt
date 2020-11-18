package com.chebureck.playlist.db

data class PlaylistWrapper(
    private val playlistEntity: PlaylistEntity,
) : Playlist {
    private lateinit var tracks: List<TrackWrapper>

    override val name: String
        get() = playlistEntity.name

    override val id: Long
        get() = playlistEntity.id

    override fun deleteTrack(trackId: Int) {
    }

    override fun addTrack(trackId: Int) {
    }
}
