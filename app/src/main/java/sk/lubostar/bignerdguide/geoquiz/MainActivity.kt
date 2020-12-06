package sk.lubostar.bignerdguide.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_main)

        true_button.setOnClickListener { checkAnswer(true) }
        false_button.setOnClickListener { checkAnswer(false) }

        question_text_view.setOnClickListener { nextQuestion() }
        next_button.setOnClickListener { nextQuestion() }
        prev_button.setOnClickListener { prevQuestion() }

        updateQuestion()
    }

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun prevQuestion() {
        currentIndex = (currentIndex + questionBank.size - 1) % questionBank.size
        updateQuestion()
    }

    private fun updateQuestion() = question_text_view.setText(questionBank[currentIndex].textResId)

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}