package interfaces

import classes.Track

/* БД
таблица: список плейлистов
поля:
название плейлиста; ID
таблица: список треков
поля:
название трека, автор,  ID
таблица: плейлисты
поля: ID трека, ID плейлиста */
interface Playlist {
    var tracks: ArrayList<Track>

    var name: String

    val id: Int

    fun deleteTrack(trackId: Int)

    fun addTrack(trackId: Int)
}
