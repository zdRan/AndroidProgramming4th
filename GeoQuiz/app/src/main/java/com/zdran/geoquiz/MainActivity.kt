package com.zdran.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    //问题列表
    private val questionBank = listOf(
        Question(R.string.question_1, true),
        Question(R.string.question_2, false),
        Question(R.string.question_3, true),
        Question(R.string.question_4, false),
        Question(R.string.question_5, true)
    )

    //当前问题的下标
    private var currentIndex = 0

    //当前答题的数目
    private var currentAnswerCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")

        //设置视图
        setContentView(R.layout.activity_main)

        //绑定组件
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        //设置监听
        trueButton.setOnClickListener {
            checkAnswer(true)
            afterAnswer()
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            afterAnswer()
        }

        nextButton.setOnClickListener {
            //重新渲染问题
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        //视图渲染
        //1、设置问题内容
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart:  called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: called")
    }

    /**
     * 刷新问题
     */
    private fun updateQuestion() {
        val questionResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionResId)
        Log.d(TAG, "updateQuestion: " + questionBank[currentIndex].answered)
        //禁用按钮
        falseButton.isEnabled = !questionBank[currentIndex].answered
        trueButton.isEnabled = !questionBank[currentIndex].answered

    }

    /**
     * 检查用户的答案
     */
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (correctAnswer == userAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        //更新得分
        questionBank[currentIndex].score =
            if (correctAnswer == userAnswer) 100 / questionBank.size else 0
    }

    private fun afterAnswer(){
        //更新答题数目
        currentAnswerCount++
        //判断所有问题是否都已经回答完毕
        if (currentAnswerCount == questionBank.size) {
            showScore()
        }
        //禁用问题
        questionBank[currentIndex].answered = true
        //禁用按钮
        trueButton.isEnabled = false
        falseButton.isEnabled = false
    }
    private fun showScore() {
        val totalScore = questionBank.sumBy { question -> question.score }
        Toast.makeText(this, "score:$totalScore", Toast.LENGTH_LONG).show()

    }
}
