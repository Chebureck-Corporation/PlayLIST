package interfaces

//БД
//таблица: список плейлистов
//поля:
//название плейлиста; ID
//таблица: список треков
//поля:
//название трека, автор,  ID
//таблица: плейлисты
//поля: ID трека, ID плейлиста

interface Playlist {

    fun delete()

    fun rename(newName: String)

}