package sk.lubostar.bignerdguide.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        private const val KEY_INDEX = "save_index_key"
        private const val REQUEST_CODE_CHEAT = 0
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
        cheat_button.setOnClickListener {
            startActivityForResult(viewModel.getCheatIntent(this), REQUEST_CODE_CHEAT)
        }
        updateCheatCountText()

        viewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        updateQuestion()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX,  viewModel.currentIndex)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == REQUEST_CODE_CHEAT){
            viewModel.isCheater(data)
            updateCheatCountText()
        }
    }

    private fun updateCheatCountText(){
        cheat_counter.text = getString(R.string.cheats_count, viewModel.cheatsCount)
        if(viewModel.cheatsCount == 0){
            cheat_button.isEnabled = false
        }
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