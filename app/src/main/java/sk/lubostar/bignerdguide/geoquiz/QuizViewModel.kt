package sk.lubostar.bignerdguide.geoquiz

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel

class QuizViewModel: ViewModel() {
    companion object{
        private const val MAX_NUMBER_CHEATS_AVAILABLE = 3
    }

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    var currentIndex = 0
    var numberOfAnswered = 0
    var numberOfCorrect = 0

    val currentQuestionTextResId: Int
        get() = questionBank[currentIndex].textResId

    val currentQuestionAnswered: Boolean
        get() = questionBank[currentIndex].answered

    val allQuestionsAnswered: Boolean
        get() = numberOfAnswered == questionBank.size

    val cheatsCount: Int
        get() = MAX_NUMBER_CHEATS_AVAILABLE - questionBank.filter { it.cheated }.count()

    fun getCheatIntent(context: Context): Intent{
        with(questionBank[currentIndex]){
            return CheatActivity.newIntent(context, answer, cheated)
        }
    }

    fun isCheater(data: Intent?){
        questionBank[currentIndex].cheated =
            data?.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false) ?: false
    }

    fun answerQuestion(userAnswer: Boolean) : Int{
        numberOfAnswered++

        val correctAnswer = with(questionBank[currentIndex]){
            answered = true
            answer
        }

        if(userAnswer == correctAnswer) numberOfCorrect++

        return when {
            questionBank[currentIndex].cheated -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
    }

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev(){
        currentIndex = (currentIndex + questionBank.size - 1) % questionBank.size
    }
}