package edu.mwsu.csmp.cwilson39.csc445_geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import edu.mwsu.csmp.cwilson39.csc445_geoquiz.databinding.ActivityCheatBinding

const val EXTRA_ANSWER_SHOWN = "edu.mwsu.csmp.cwilson39.csc445_geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "edu.mwsu.csmp.cwilson39.csc445_geoquiz.answer_is_true"

private const val TAG = "CheatActivity"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding
    private val cheatViewModel: CheatViewModel by viewModels()

    private var answerIsTrue = false

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a CheatViewModel: $cheatViewModel")

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            cheatViewModel.showAnswerClicked = true
            binding.answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }

        if (cheatViewModel.showAnswerClicked) {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else         -> R.string.false_button
            }
            binding.answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }

    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

}