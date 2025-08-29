package com.example.lhythm.data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lhythm.constants.Constants

@Entity(tableName = Constants.SONGTOIMGTABLE)
data class SongToImage(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    val songPath: String,
    val songTitle: String,
    val notes: String,
    val songAuthor: String,
    val imgPath: String
)
