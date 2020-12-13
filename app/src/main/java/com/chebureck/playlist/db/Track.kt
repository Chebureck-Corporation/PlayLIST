package com.chebureck.playlist.db

import android.os.Parcel
import android.os.Parcelable

data class Track (
    val name: String,
    val author: String,
    val id: String,
) : Parcelable {
    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(author)
        dest.writeString(id)
    }

    companion object {
        val CREATOR = object : Parcelable.Creator<Track> {
            override fun createFromParcel(source: Parcel): Track {
                val name = source.readString()
                val author = source.readString()
                val id = source.readString()
                return Track(name!!, author!!, id!!)
            }

            override fun newArray(size: Int): Array<Track> {
                return Array(size) { Track("", "", "") }
            }

        }
    }
}
