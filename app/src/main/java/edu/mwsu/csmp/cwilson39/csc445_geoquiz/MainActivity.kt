package edu.mwsu.csmp.cwilson39.csc445_geoquiz

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import edu.mwsu.csmp.cwilson39.csc445_geoquiz.databinding.ActivityMainBinding
import java.lang.ref.Reference
import java.math.BigDecimal
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        // Welcome
        displayWelcome("Welcome to GeoQuiz!")

        binding.trueButton.setOnClickListener {
            checkAnswer(true, quizViewModel.currentIndex)
            quizViewModel.questionBank[quizViewModel.currentIndex].answered = true
            isAnswered(quizViewModel.currentIndex)
            isEnd()
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false, quizViewModel.currentIndex)
            quizViewModel.questionBank[quizViewModel.currentIndex].answered = true
            isAnswered(quizViewModel.currentIndex)
            isEnd()
        }

        updateQuestion()
        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            isAnswered(quizViewModel.currentIndex)
            updateQuestion()
        }

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            isAnswered(quizViewModel.currentIndex)
            updateQuestion()
        }

        binding.prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            isAnswered(quizViewModel.currentIndex)
            updateQuestion()
        }

        binding.cheatButton.setOnClickListener {
            // Start CheatActivity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

    } // End of onCreate

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean, index: Int) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val answerTextResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_message
            else -> R.string.incorrect_message
        }
        if(userAnswer == correctAnswer) {
            quizViewModel.numCorrect++
        }
        if(quizViewModel.numAnswered <= quizViewModel.questionBank.size) {
            quizViewModel.numAnswered++
        }
        Log.d(TAG,"------------------------")
        Log.d(TAG,"currentIndex:  ${quizViewModel.currentIndex}")
        Log.d(TAG, "numCorrect: ${quizViewModel.numCorrect}")
        Log.d(TAG, "numAnswered: ${quizViewModel.numAnswered}")
        Snackbar.make(binding.root, answerTextResId, Snackbar.LENGTH_SHORT).show()
    }

    private fun displayWelcome(welcomeMsg : String) {
        Toast.makeText(
            this,
            welcomeMsg,
            Toast.LENGTH_SHORT)
            .show()
    }

    private fun isAnswered(index:Int){
        if (quizViewModel.questionBank[index].answered){
            binding.trueButton.isEnabled=false
            binding.falseButton.isEnabled=false
        }else{
            binding.trueButton.isEnabled=true
            binding.falseButton.isEnabled=true
        }
        Log.d(TAG, "isAnswered: ${quizViewModel.questionBank[quizViewModel.currentIndex].answered}")
    }

    private fun isEnd() {
        val percent = (quizViewModel.numCorrect * 100) / quizViewModel.numAnswered
        val grade = "${quizViewModel.numCorrect}/${quizViewModel.numAnswered} or %$percent"
        Log.d(TAG, grade)

        if (quizViewModel.numAnswered == quizViewModel.questionBank.size) {
            Toast.makeText(this, grade, Toast.LENGTH_SHORT).show()
        }
    }

} // End of MainActivity class.