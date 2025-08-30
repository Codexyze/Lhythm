package com.example.lhythm.presentation.Navigation

import kotlinx.serialization.Serializable

@Serializable
object SAMPLESCREEN

@Serializable
object ONBOARDING

@Serializable
object HOMESCREEN

@Serializable
data class USERPLAYLISTSCREEN(val playListID: Int)

@Serializable
data class LYRICSFULLSCREEN(val lyrics : String)

@Serializable
object SOUNDFXSCREEN

@Serializable
object ALARAMSETTINGSCREEN

@Serializable
data class AUDIOTRIMMERSCREEN(val uri: String , val songDuration : Long)

@Serializable
object GETALLIMAGESCREEN

@Serializable
data class IMAGETOSONGMAPSCREEN(val Imgpath: String)


@Serializable
object MEMEORYBOXSELECTIONSCREEN

@Serializable
object VIEWMEMORYSCREEN

@Serializable
data class MEMORYNOTESSCREEN(
    val id:Int ,
    val songPath: String,
    val songTitle: String,
    val notes: String,
    val songAuthor: String,
    val imgPath: String
)
