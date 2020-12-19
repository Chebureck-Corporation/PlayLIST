package com.chebureck.playlist.network.api.spotify.`object`

class SpotifyApiPlaylist(
    var name: String,
    var id: String,
    private var images: List<Image>?,
    var tracks: Tracks
) {
    val imageUrl: String?
        get() = images?.let { it[0].url }
}

data class Image(
    val url: String
)

data class Tracks(
    val href: String
)