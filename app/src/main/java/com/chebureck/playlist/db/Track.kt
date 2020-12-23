package com.chebureck.playlist.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Track(
    val spotifyTrackId: String,
    val name: String,
    val author: String,
    val uri: String,
    @PrimaryKey(autoGenerate = true)
    val trackId: Long = 0
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(spotifyTrackId)
        parcel.writeString(name)
        parcel.writeString(author)
        parcel.writeString(uri)
        parcel.writeLong(trackId)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Track

        if (spotifyTrackId != other.spotifyTrackId) return false
        if (name != other.name) return false
        if (author != other.author) return false
        if (uri != other.uri) return false

        return true
    }

    override fun hashCode(): Int {
        var result = spotifyTrackId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + uri.hashCode()
        return result
    }

    companion object CREATOR : Parcelable.Creator<Track> {
        override fun createFromParcel(parcel: Parcel): Track {
            return Track(parcel)
        }

        override fun newArray(size: Int): Array<Track?> {
            return arrayOfNulls(size)
        }
    }
}
