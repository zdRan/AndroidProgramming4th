package com.zdran.geoquiz

import androidx.annotation.StringRes

/**
 * textResId:问题资源ID
 * answer：答案
 * answered：是否已经回答过
 */
data class Question(@StringRes val textResId: Int, val answer: Boolean){
    //是否回答过
    var answered: Boolean=false
    //得分
    var score :Int = 0
}
