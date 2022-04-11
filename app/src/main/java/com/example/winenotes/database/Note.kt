package com.example.winenotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NOTE (
    @PrimaryKey(autoGenerate = true) val id : Long,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "notes") val notes : String,
    @ColumnInfo(name = "last_modified") val lastModified : String
){

    override fun toString(): String {
        return "${title}, ${notes} (${id})"
    }
}