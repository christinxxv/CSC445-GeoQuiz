package edu.mwsu.csmp.cwilson39.csc445_geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
const val NUM_CORRECT ="NUM_CORRECT"
const val NUM_ANSWERED = "NUM_ANSWERED"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    /* --------------- QUESTION BANK --------------- */
    val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))



     var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    var numCorrect: Int
        get() = savedStateHandle.get(NUM_CORRECT) ?: 0
        set(value) = savedStateHandle.set(NUM_CORRECT, value)

    var numAnswered: Int
        get() = savedStateHandle.get(NUM_ANSWERED) ?: 0
        set(value) = savedStateHandle.set(NUM_ANSWERED, value)



    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].questionTextResId



    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    fun moveToNext() {
        currentIndex = (currentIndex+1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex + questionBank.size - 1) % questionBank.size
    }

}