package com.hi_lo

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val name: String = "",
    val slope: Int = 72,
    val rating: Float = 72.0f,
    val par: Int = 72,
    val holes: List<Hole> = emptyList()
)

@Serializable
data class Hole(
    @SerializedName("hole_number") val holeNumber: Int,
    @SerializedName("hole_handicap") val holeHandicap: Int,
    @SerializedName("hole_par") val holePar: Int
)
