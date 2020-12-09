package sk.lubostar.bignerdguide.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel: ViewModel() {
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

    fun answerQuestion(userAnswer: Boolean) : Int{
        numberOfAnswered++

        val correctAnswer = with(questionBank[currentIndex]){
            answered = true
            answer
        }

        return if (userAnswer == correctAnswer) {
            numberOfCorrect++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
    }

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev(){
        currentIndex = (currentIndex + questionBank.size - 1) % questionBank.size
    }
}