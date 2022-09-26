package edu.mwsu.csmp.cwilson39.csc445_geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "CheatViewModel"
const val ANSWER_CLICKED = "ANSWER_CLICKED"

class CheatViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    var showAnswerClicked: Boolean
        get() = savedStateHandle.get(ANSWER_CLICKED) ?: false
        set(value) = savedStateHandle.set(ANSWER_CLICKED, value)
}