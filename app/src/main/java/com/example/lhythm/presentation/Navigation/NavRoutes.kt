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