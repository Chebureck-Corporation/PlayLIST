package com.chebureck.playlist.db

data class TrackWrapper(
    private val trackEntity: TrackEntity
) : Track {
    override val name: String
        get() = trackEntity.name
    override val author: String
        get() = trackEntity.author
    override val id: Long
        get() = trackEntity.id

    override fun toString(): String {
        return "$author - $name"
    }
}
