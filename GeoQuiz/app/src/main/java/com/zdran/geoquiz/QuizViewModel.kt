package com.zdran.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    //问题列表
    private val questionBank = listOf(
        Question(R.string.question_1, true),
        Question(R.string.question_2, false),
        Question(R.string.question_3, true),
        Question(R.string.question_4, false),
        Question(R.string.question_5, true)
    )

    //当前问题的下标
    var currentIndex = 0

    //当前用户是否作弊
    var isCheater = false

    val currentIndexAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentIndexTextId: Int
        get() = questionBank[currentIndex].textResId

    //当前题目是否展示过答案
    val currentAnswerShown: Boolean
        get() = questionBank[currentIndex].answerShown

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun setCurrentAnswerShown(answerShown: Boolean) {
        questionBank[currentIndex].answerShown = answerShown
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: called")
    }
}