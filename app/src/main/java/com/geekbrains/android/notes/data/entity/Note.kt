package com.geekbrains.android.notes.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Note(
        val id: String = "",
        val title: String = "",
        val text: String = "",
        val color: Color = Color.WHITE,
        val lastChanged: Date = Date()
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false
        return true
    }

    override fun hashCode() = id.hashCode()

    enum class Color {
        WHITE,
        YELLOW,
        GREEN,
        BLUE,
        RED,
        VIOLET
    }
}