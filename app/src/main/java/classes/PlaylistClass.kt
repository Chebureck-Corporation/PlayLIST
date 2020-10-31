package classes

import interfaces.Playlist

class PlaylistClass(private var name: String, private var tracks: ArrayList<Track>,
                    private val id: Int) : Playlist {
    companion object {
        fun or(playlists: ArrayList<PlaylistClass>) {}

        fun and(playlists: ArrayList<PlaylistClass>) {}

        fun xor(playlists: ArrayList<PlaylistClass>) {}

        fun getNameById(id: Int) {}
    }
    override fun rename(newName: String) {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }
}
