package com.chebureck.playlist.network.api.spotify.`object`

data class TracksInfo(
    val track: SpotifyTrack?
)

data class SpotifyTrack(
    val name: String,
    val id: String,
    val artists: List<Artist>,
    val uri: String
)

data class Artist(
    val name: String
)

data class TrackUris(
    val uris: List<String>
)
