package edu.mwsu.csmp.cwilson39.csc445_geoquiz

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import edu.mwsu.csmp.cwilson39.csc445_geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayWelcome("Welcome to GeoQuiz!")

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        updateQuestion()
        binding.nextButton.setOnClickListener {
            nextQuestion()
        }

        binding.questionTextView.setOnClickListener {
            nextQuestion()
        }

        binding.prevButton.setOnClickListener {
            if(currentIndex == 0) currentIndex = questionBank.size - 1
            else currentIndex = (currentIndex - 1) % questionBank.size
            updateQuestion()
        }

    } /* END OF onCreate*/

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].questionTextResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val answerTextResId = when(userAnswer) {
            correctAnswer -> R.string.correct_message
            else -> R.string.incorrect_message
        }
        Snackbar.make(binding.root, answerTextResId, Snackbar.LENGTH_SHORT).show()
    }

    private fun nextQuestion() {
        currentIndex = (currentIndex+1) % questionBank.size
        updateQuestion()
    }

    private fun displayWelcome(welcomeMsg : String) {
        Toast.makeText(
            this,
            welcomeMsg,
            Toast.LENGTH_SHORT)
            .show()
    }

}