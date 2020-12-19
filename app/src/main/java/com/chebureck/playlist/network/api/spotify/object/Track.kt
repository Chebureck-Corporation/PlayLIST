package com.chebureck.playlist.network.api.spotify.`object`

data class TracksInfo(
    var track: TrackInfo
)

data class TrackInfo(
    var name: String,
    var id: String,
    var artists: List<Artist>
)

data class Artist(
    var name: String
)