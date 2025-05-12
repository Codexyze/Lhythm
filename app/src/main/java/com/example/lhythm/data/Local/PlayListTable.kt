package com.example.lhythm.data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lhythm.constants.Constants

@Entity(tableName = Constants.PLAYLISTTABLE)
data class PlayListTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val playListName: String,
)
