package com.hi_lo.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hi_lo.data.Course
import com.hi_lo.data.Hole

data class Team(val golfer1: Golfer, val golfer2: Golfer)

class Golfer(val name: String, val hcp: Int)

data class Score(val playerNumber: Int, var strokes: Int = 0, var points: Int = 0)

class MatchViewModel : ViewModel() {

    private var _title: MutableLiveData<String> = MutableLiveData<String>("Course Select")
    val title: LiveData<String> get() = _title

    var team1: Team? = null
    var team2: Team? = null

    var pricePerPoint: MutableLiveData<Int> = MutableLiveData(1)
    var selectedCourse: Course? = null

    val team1Score: MutableLiveData<Int> = MutableLiveData(0)
    val team2Score: MutableLiveData<Int> = MutableLiveData(0)
    private val currentHole: MutableLiveData<Int> = MutableLiveData(1)

    fun addPointsToTeam1Score(pts: Int) {
        team1Score.value = team1Score.value?.plus(pts)
    }

    fun addPointsToTeam2Score(pts: Int) {
        team2Score.value = team2Score.value?.plus(pts)
    }

    fun setupMatch() {
        this._title.value = "Setup Match @ ${selectedCourse!!.name}"
    }

    fun startMatch(
        useCourseHandicap: Boolean,
        name1: String,
        hcp1: Int,
        name2: String,
        hcp2: Int,
        team2: Team
    ) {
        if (useCourseHandicap) {
            this.team1 = Team(Golfer(name1, hcp1), Golfer(name2, hcp2))
        } else {
            this.team1 = Team(
                Golfer(name1, calculateGolferHandicap(hcp1)),
                Golfer(name2, calculateGolferHandicap(hcp2))
            )
        }
        this.team2 = team2
    }

    fun currentHole(): Hole {
        return selectedCourse!!.holes[currentHole.value!!.minus(1)]
    }

    fun hasNextHole(): Boolean {
        return currentHole.value!! < 18
    }

    fun nextHole() {
        currentHole.value = currentHole.value?.inc()
    }

    fun getFinalScore(): String {
        pricePerPoint.value?.let { price ->
            team1Score.value?.let { t1 ->
                team2Score.value?.let { t2 ->
                    val diff = t1.minus(t2)
                    if (diff > 0) return "Team 2 owes Team 1 $${diff.times(price)}"
                    else if (diff < 0) return "Team 1 owes Team 2 $${diff.times(price).times(-1)}"
                    else return "All tied up"
                }
            }
        }
        return "Something went wrong, figure it out yourself."
    }

    /**
     * Current Score
     *
     * A positive value means team 1 is winning, a negative value means team two is winning
     */
    private fun currentScore() = team2Score.value?.let { team1Score.value!!.minus(it) }

    fun resetMatch() {
        team1Score.value = 0
        team2Score.value = 0
        currentHole.value = 1
        _title.value = "Setup Match"
    }

    private fun calculateGolferHandicap(index: Int): Int {
        selectedCourse?.let { course ->
            return index.times(course.slope).div(113).plus(course.rating).minus(72).toInt()
        }
        throw IllegalStateException("Course must be selected")
    }

}