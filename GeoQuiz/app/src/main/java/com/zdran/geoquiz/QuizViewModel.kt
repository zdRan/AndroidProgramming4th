package com.zdran.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: called")
    }
}