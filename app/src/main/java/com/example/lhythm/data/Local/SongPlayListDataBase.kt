package com.example.lhythm.data.Local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SongEntity::class], version = 1)
abstract class SongPlayListDataBase: RoomDatabase() {
    abstract fun SongPlayListDao(): SongDao
}