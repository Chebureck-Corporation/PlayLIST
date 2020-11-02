package com.chebureck.playlist.db

/* БД
таблица: плейлисты
поля:
название плейлиста; ID
таблица: треки
поля:
название трека, автор,  ID
таблица: плейлисты_треки
поля: ID трека, ID плейлиста */
interface Playlist {
    var tracks: List<Track>

    var name: String

    val id: Int

    fun deleteTrack(trackId: Int)

    fun addTrack(trackId: Int)
}
