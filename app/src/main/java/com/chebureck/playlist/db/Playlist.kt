package com.chebureck.playlist.db

import android.os.Parcel
import android.os.Parcelable

data class Playlist (
    val name: String,
    val imageUrl: String?,
    val id: String
) : Parcelable {
    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(imageUrl)
        dest.writeString(id)
    }

    companion object {
        val CREATOR = object : Parcelable.Creator<Playlist> {
            override fun createFromParcel(source: Parcel): Playlist {
                val name = source.readString()
                val imageUrl = source.readString()
                val id = source.readString()
                return Playlist(name!!, imageUrl, id!!)
            }

            override fun newArray(size: Int): Array<Playlist?> {
                return Array(size) { Playlist("", "", "") }
            }
        }
    }
}
