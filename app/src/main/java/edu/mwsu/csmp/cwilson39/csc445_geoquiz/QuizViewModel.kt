package edu.mwsu.csmp.cwilson39.csc445_geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
const val NUM_CORRECT ="NUM_CORRECT"
const val NUM_ANSWERED = "NUM_ANSWERED"
const val CHEATED_LIST = "CHEATED_LIST"
const val ANSWERED_LIST = "ANSWERED_LIST"


class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    /* --------------- QUESTION BANK --------------- */

    val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    /* --------------- GETTERS/SETTERS --------------- */

    var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    var numCorrect: Int
        get() = savedStateHandle.get(NUM_CORRECT) ?: 0
        set(value) = savedStateHandle.set(NUM_CORRECT, value)

    var numAnswered: Int
        get() = savedStateHandle.get(NUM_ANSWERED) ?: 0
        set(value) = savedStateHandle.set(NUM_ANSWERED, value)

    var cheatedList: MutableList<Boolean>
        get() = savedStateHandle.get(CHEATED_LIST) ?: mutableListOf(false, false, false, false, false,false)
        set(value) = savedStateHandle.set(CHEATED_LIST, value)

    var answeredList: MutableList<Boolean>
        get() = savedStateHandle.get(ANSWERED_LIST) ?: mutableListOf(false, false, false, false, false, false)
        set(value) = savedStateHandle.set(ANSWERED_LIST, value)

    /* --------------- GETTERS --------------- */

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].questionTextResId

    val currentAnswered: Boolean
        get() = answeredList[currentIndex]

    val currentCheated: Boolean
        get() = cheatedList[currentIndex]

   /* --------------- LOG --------------- */

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }

    /* --------------- FUNCTIONS --------------- */
    fun moveToNext() {
        currentIndex = (currentIndex+1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex + questionBank.size - 1) % questionBank.size
    }

    fun updateAsCheated() {
        val tempCheatedList = cheatedList
        tempCheatedList[currentIndex] = true
        cheatedList = tempCheatedList
    }

    fun updateAsAnswered() {
        val tempAnsweredList = answeredList
        tempAnsweredList[currentIndex] = true
        answeredList = tempAnsweredList
    }

}