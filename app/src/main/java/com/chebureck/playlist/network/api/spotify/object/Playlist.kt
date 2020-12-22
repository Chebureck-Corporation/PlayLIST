package com.chebureck.playlist.network.api.spotify.`object`

class SpotifyApiPlaylist(
    val name: String,
    val id: String,
    private val images: List<Image>?,
    val tracks: Tracks
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

data class SpotifyApiPlaylistCreate(
    val name: String
)