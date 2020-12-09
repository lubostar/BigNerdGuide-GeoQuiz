package sk.lubostar.bignerdguide.geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean){
    var answered: Boolean = false
    var cheated: Boolean = false
}