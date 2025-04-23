package com.hi_lo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hi_lo.Course
import com.hi_lo.data.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class MatchData(
    val team1: Team?,
    val team2: Team?,
    val currentHole: Int = 1,
    val pricePerPoint: Int = 1
)

@Serializable
data class Team(val golfer1: Golfer, val golfer2: Golfer)

@Serializable
class Golfer(val name: String, val hcp: Int)

data class Score(val playerNumber: Int, var strokes: Int = 0, var points: Int = 0)

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val repository: MatchRepository
) : ViewModel() {

    private var _title: MutableLiveData<String> = MutableLiveData<String>("Hi-Lo")
    val title: LiveData<String> get() = _title

    var team1: Team? = null
    var team2: Team? = null

    var pricePerPoint: MutableLiveData<Int> = MutableLiveData(1)

    private val _selectedCourse = MutableStateFlow<Course?>(null)
    val selectedCourse: StateFlow<Course?> = _selectedCourse


    val team1Score: MutableLiveData<Int> = MutableLiveData(0)
    val team2Score: MutableLiveData<Int> = MutableLiveData(0)
    private val currentHole: MutableLiveData<Int> = MutableLiveData(1)

    fun saveMatchData(matchData: MatchData) {
        viewModelScope.launch {
            repository.saveMatchData(matchData)
        }
    }

    suspend fun loadMatchData(): MatchData? {
        return repository.loadMatchData()
    }
    fun addPointsToTeam1Score(pts: Int) {
        team1Score.value = team1Score.value?.plus(pts)
    }

    fun addPointsToTeam2Score(pts: Int) {
        team2Score.value = team2Score.value?.plus(pts)
    }

    fun startMatch(
        useCourseHandicap: Boolean,
        team1: Team,
        team2: Team
    ) {
        if (useCourseHandicap) {
//            this.team1 = Team(Golfer(name1, hcp1), Golfer(name2, hcp2))
        } else {
//            this.team1 = Team(
//                Golfer(name1, calculateGolferHandicap(hcp1)),
//                Golfer(name2, calculateGolferHandicap(hcp2))
//            )
        }
        this.team1 = team1
        this.team2 = team2
        val matchData = MatchData(team1, team2, 1, 1)
        saveMatchData(matchData = matchData)
    }

//    fun currentHole(): Hole {
//        return selectedCourse!!.holes[currentHole.value!!.minus(1)]
//    }

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


//    private fun calculateGolferHandicap(index: Int): Int {
//        selectedCourse?.let { course ->
//            return index.times(course.slope).div(113).plus(course.rating).minus(72).toInt()
//        }
//        throw IllegalStateException("Course must be selected")
//    }

    fun selectCourse(course: Course) {
        _selectedCourse.value = course
    }

}