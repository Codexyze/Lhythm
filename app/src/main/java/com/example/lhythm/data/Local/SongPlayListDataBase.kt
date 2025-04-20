package com.example.lhythm.data.Local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SongEntity::class, FavSongEntity::class], version = 2)
abstract class SongPlayListDataBase: RoomDatabase() {
    abstract fun SongPlayListDao(): SongDao
    abstract fun FavSongDao(): FavSongDao
}