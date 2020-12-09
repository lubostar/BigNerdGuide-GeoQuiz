package sk.lubostar.bignerdguide.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        const val KEY_INDEX = "save_index_key"
    }

    private val viewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        true_button.setOnClickListener { checkAnswer(true) }
        false_button.setOnClickListener { checkAnswer(false) }

        question_text_view.setOnClickListener { nextQuestion() }
        next_button.setOnClickListener { nextQuestion() }
        prev_button.setOnClickListener { prevQuestion() }

        viewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        updateQuestion()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX,  viewModel.currentIndex)
    }

    private fun nextQuestion(){
        viewModel.moveToNext()
        updateQuestion()
    }

    private fun prevQuestion(){
        viewModel.moveToPrev()
        updateQuestion()
    }

    private fun updateQuestion(){
        with(viewModel){
            question_text_view.setText(currentQuestionTextResId)
            if (currentQuestionAnswered){
                disableButtons()
            }else {
                enableButtons()
            }
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        disableButtons()

        with(viewModel){
            val messageResId = answerQuestion(userAnswer)

            if(allQuestionsAnswered) {
                Toast.makeText(this@MainActivity,
                    getString(R.string.result_text, numberOfCorrect, numberOfAnswered),
                    Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this@MainActivity, messageResId, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun disableButtons(){
        false_button.isEnabled = false
        true_button.isEnabled = false
    }

    private fun enableButtons(){
        false_button.isEnabled = true
        true_button.isEnabled = true
    }
}