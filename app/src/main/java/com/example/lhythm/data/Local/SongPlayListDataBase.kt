package com.example.lhythm.data.Local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SongEntity::class, FavSongEntity::class, PlayListTable::class,
    PlayListSongMapper::class, SongToImage::class], version = 6)
abstract class SongPlayListDataBase: RoomDatabase() {
    abstract fun SongPlayListDao(): SongDao
    abstract fun FavSongDao(): FavSongDao
    abstract fun PlayListDao(): PlayListDao
    abstract fun PlayListSongsDao(): PlayListSongs

    abstract fun SongToImageDao(): SongToImgDao
}