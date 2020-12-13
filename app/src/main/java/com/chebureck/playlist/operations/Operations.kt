package com.chebureck.playlist.operations

import com.chebureck.playlist.db.Playlist
import com.chebureck.playlist.db.PlaylistEntity
import com.chebureck.playlist.db.Track

class Operations {
    fun And(tracksList: List<MutableList<Track>?>): MutableList<Track>?{
        var newTracks: MutableList<Track>? = tracksList[0]
        if (newTracks != null)
            for (i in 1..tracksList.size){
                val curTracks: MutableList<Track>? = newTracks
                val tempTracks: MutableList<Track>? = tracksList[i]
                newTracks = mutableListOf()
                if (curTracks != null && tempTracks != null) {
                    for (j in 0..curTracks.size){
                        if (curTracks[j] in tempTracks)
                            newTracks.add(curTracks[j])
                    }
                }
            }
        return newTracks
    }

    fun Or(tracksList: List<MutableList<Track>?>): MutableList<Track>?{
        val newTracks: MutableList<Track>? = tracksList[0]
        if (newTracks != null)
            for (i in 1..tracksList.size){
                val tempTracks: MutableList<Track>? = tracksList[i]
                if (tempTracks != null) {
                    for (j in 0..tempTracks.size){
                        if (tempTracks[j] !in newTracks)
                            newTracks.add(tempTracks[j])
                    }
                }
            }
        return newTracks
    }

    fun Xor(tracksList: List<MutableList<Track>?>): MutableList<Track>?{
        var newTracks: MutableList<Track>? = tracksList[0]
        if (newTracks != null)
            for (i in 1..tracksList.size){
                val curTracks: MutableList<Track>? = newTracks
                val tempTracks: MutableList<Track>? = tracksList[i]
                val orTracks: MutableList<Track> = mutableListOf()
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
        return newTracks
    }
}