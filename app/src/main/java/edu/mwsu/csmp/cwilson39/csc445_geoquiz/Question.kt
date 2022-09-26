package edu.mwsu.csmp.cwilson39.csc445_geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val questionTextResId: Int, val answer: Boolean,
                    var answered: Boolean = false, var cheated: Boolean = false)
