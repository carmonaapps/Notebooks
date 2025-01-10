package com.example.notebook.data

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var title: String,
    var content: String,
    var timestamp: Long = System.currentTimeMillis()
) : Parcelable
