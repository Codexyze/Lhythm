package com.example.lhythm.data.Local

import androidx.room.RoomDatabase

abstract class SongPlayListDataBase: RoomDatabase() {
    abstract fun SongPlayListDao(): SongDao
}