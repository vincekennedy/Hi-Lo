package com.hi_lo

import com.google.gson.annotations.SerializedName

data class Course(
    val name: String,
    val slope: Int,
    val rating: Float,
    val par: Int,
    val holes: List<Hole>
)

data class Hole(
    @SerializedName("hole_number") val holeNumber: Int,
    @SerializedName("hole_handicap") val holeHandicap: Int,
    @SerializedName("hole_par") val holePar: Int
)
