package com.example.lhythm.presentation.Navigation

import kotlinx.serialization.Serializable

@Serializable
object SAMPLESCREEN

@Serializable
object ONBOARDING

@Serializable
data class MUSICPLAYERSCREEN(
    val path: String
)