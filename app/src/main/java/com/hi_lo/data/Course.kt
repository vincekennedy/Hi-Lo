package com.hi_lo.data

data class Course(
    val name: String,
    val slope: Int,
    val rating: Float,
    val par: Int,
    val holes: List<Hole>
)

data class Hole(
    val holeNumber: Int,
    val holeHandicap: Int,
    val holePar: Int
)
