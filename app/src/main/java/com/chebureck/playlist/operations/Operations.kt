package com.chebureck.playlist.operations

import com.chebureck.playlist.db.Playlist
import com.chebureck.playlist.db.PlaylistEntity
import com.chebureck.playlist.db.Track

class Operations {
    fun and(tracksList: List<List<Track>>): List<Track> {
        val newTracks = tracksList[0].toMutableList()
        for (tempTracks in tracksList) {
            for (tempTrack in tempTracks) {
                if (tempTrack !in newTracks) {
                    newTracks.remove(tempTrack)
                }
            }
        }
        return newTracks
    }

    fun or(tracksList: List<List<Track>>): List<Track> {
        val newTracks = tracksList[0].toMutableList()
        for (tempTracks in tracksList) {
            for (tempTrack in tempTracks) {
                if (tempTrack !in newTracks) {
                    newTracks.add(tempTrack)
                }
            }
        }
        return newTracks
    }

    fun xor(tracksList: List<List<Track>>): List<Track> {
        val newTracks = tracksList[0].toMutableList()
        for (tempTracks in tracksList) {
            for (tempTrack in tempTracks) {
                if (tempTrack in newTracks) {
                    newTracks.remove(tempTrack)
                } else {
                    newTracks.add(tempTrack)
                }
            }
        }
        return newTracks
    }
}