package com.example.itcenter.model

import java.io.Serializable

data class DarslarModel(
    val id: Int,
    val videoLink: String,
    val language: String,
    val lessonName: String,
    val level: String
): Serializable
