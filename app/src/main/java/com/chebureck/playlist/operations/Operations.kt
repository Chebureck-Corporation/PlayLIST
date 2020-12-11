package com.chebureck.playlist.operations

import com.chebureck.playlist.db.PlaylistEntity
import com.chebureck.playlist.db.PlaylistWrapper
import com.chebureck.playlist.db.TrackWrapper

class Operations {
    fun And(playlists: List<PlaylistWrapper>, id: Int, name: String): PlaylistWrapper{
        var newTracks: MutableList<TrackWrapper>? = playlists[0].tracks.value
        if (newTracks != null)
            for (i in 1..playlists.size){
                val curTracks: MutableList<TrackWrapper>? = newTracks
                val tempTracks: MutableList<TrackWrapper>? = playlists[i].tracks.value
                newTracks = mutableListOf()
                if (curTracks != null && tempTracks != null) {
                    for (j in 0..curTracks.size){
                        if (curTracks[j] in tempTracks)
                            newTracks.add(curTracks[j])
                    }
                }
            }
        val result = PlaylistWrapper(PlaylistEntity((id - 1).toString(), name))
        result.tracks.value = newTracks
        return result
    }

    fun Or(playlists: List<PlaylistWrapper>, id: Int, name: String): PlaylistWrapper{
        val newTracks: MutableList<TrackWrapper>? = playlists[0].tracks.value
        if (newTracks != null)
            for (i in 1..playlists.size){
                val tempTracks: MutableList<TrackWrapper>? = playlists[i].tracks.value
                if (tempTracks != null) {
                    for (j in 0..tempTracks.size){
                        if (tempTracks[j] !in newTracks)
                            newTracks.add(tempTracks[j])
                    }
                }
            }
        val result = PlaylistWrapper(PlaylistEntity((id - 1).toString(), name))
        result.tracks.value = newTracks
        return result
    }

    fun Xor(playlists: List<PlaylistWrapper>, id: Int, name: String): PlaylistWrapper{
        var newTracks: MutableList<TrackWrapper>? = playlists[0].tracks.value
        if (newTracks != null)
            for (i in 1..playlists.size){
                val curTracks: MutableList<TrackWrapper>? = newTracks
                val tempTracks: MutableList<TrackWrapper>? = playlists[i].tracks.value
                val orTracks: MutableList<TrackWrapper> = mutableListOf()
                newTracks = mutableListOf()
                if (curTracks != null && tempTracks != null) {
                    for (j in 0..tempTracks.size){
                        if (tempTracks[j] !in curTracks)
                            orTracks.add(tempTracks[j])
                    }
                    for (j in 0..orTracks.size){
                        if (orTracks[j] in tempTracks && orTracks[j] !in curTracks || orTracks[j] in curTracks && orTracks[j] !in tempTracks)
                            newTracks.add(curTracks[j])
                    }
                }
            }
        val result = PlaylistWrapper(PlaylistEntity((id - 1).toString(), name))
        result.tracks.value = newTracks
        return result
    }
}