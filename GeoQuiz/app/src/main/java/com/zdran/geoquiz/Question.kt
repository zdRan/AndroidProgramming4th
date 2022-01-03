package com.zdran.geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean) {
    //是否展示过答案
    var answerShown: Boolean = false
}
