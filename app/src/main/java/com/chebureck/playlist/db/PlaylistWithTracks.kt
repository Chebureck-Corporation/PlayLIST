package com.chebureck.playlist.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PlaylistWithTracks(
    @Embedded val playlist: Playlist,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "trackId",
        associateBy = Junction(PlaylistTrackCrossRef::class)
    )
    val tracks: List<Track>
) : Parcelable {
    val id
        get() = playlist.playlistId

    val imageUrl
        get() = playlist.imageUrl

    val name
        get() = playlist.name

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Playlist::class.java.classLoader)!!,
        parcel.createTypedArrayList(Track)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(playlist, flags)
        parcel.writeTypedList(tracks)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<PlaylistWithTracks> {
        override fun createFromParcel(parcel: Parcel): PlaylistWithTracks {
            return PlaylistWithTracks(parcel)
        }

        override fun newArray(size: Int): Array<PlaylistWithTracks?> {
            return arrayOfNulls(size)
        }
    }
}
