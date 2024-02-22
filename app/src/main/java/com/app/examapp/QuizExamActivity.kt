package com.app.examapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.app.examapp.databinding.ActivityQuizExamBinding
import com.app.examapp.databinding.ScoreDialogBinding
import com.app.examapp.model.QuestionModel

class QuizExamActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityQuizExamBinding // declaration of binding

    companion object {
        var questionModelList: List<QuestionModel> = listOf()
        var time: String = ""
    }

    var currentQuestionIndex = 0
    var selectedAns: String? = null
    var score = 0
    val selectedAnswers = MutableList<String?>(questionModelList.size) { null }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizExamBinding.inflate(layoutInflater)   // initialization of binding
        setContentView(binding.root)

        // onclick on options and next btn
        binding.apply {
            //options
            btn0.setOnClickListener(this@QuizExamActivity)
            btn1.setOnClickListener(this@QuizExamActivity)
            btn2.setOnClickListener(this@QuizExamActivity)
            btn3.setOnClickListener(this@QuizExamActivity)
            //next btn
            nextBtn.setOnClickListener(this@QuizExamActivity)
            previousBtn.setOnClickListener(this@QuizExamActivity)
            submitBtn.setOnClickListener(this@QuizExamActivity)
        }

        loadQuestions()
        startTimer()
    }

    private fun startTimer() {
        val totalTimeInMills = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMills, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                //showing timer on timer indicator
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                //finish the quiz
                finishQuiz()
            }
        }.start()
    }

    private fun loadQuestions() {

        // Check if it's the last question
        val isLastQuestion = currentQuestionIndex == questionModelList.size - 1

        if (currentQuestionIndex == questionModelList.size) {
            finishQuiz()
            return
        }

        binding.apply {
            //question number
            questionIndicatorTextview.text = "Question ${currentQuestionIndex + 1}/ ${questionModelList.size}"
            //question progress
            questionProgressIndicator.progress =( (currentQuestionIndex + 1).toFloat() / questionModelList.size.toFloat() * 100).toInt()
            //question title
            questionTextview.text = questionModelList[currentQuestionIndex].question
            //options
            btn0.text = questionModelList[currentQuestionIndex].options[0]
            btn1.text = questionModelList[currentQuestionIndex].options[1]
            btn2.text = questionModelList[currentQuestionIndex].options[2]
            btn3.text = questionModelList[currentQuestionIndex].options[3]

            // Clear all option button selections
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))

            // Set the background color of the previously selected answer
            selectedAnswers[currentQuestionIndex]?.let { selected ->
                val selectedButton = when (selected) {
                    questionModelList[currentQuestionIndex].options[0] -> btn0
                    questionModelList[currentQuestionIndex].options[1] -> btn1
                    questionModelList[currentQuestionIndex].options[2] -> btn2
                    else -> btn3
                }
                selectedButton.setBackgroundColor(getColor(R.color.orange))
            }


            // Update visibility of Next and Submit buttons
            if (isLastQuestion) {
                nextBtn.visibility = View.GONE
                submitBtn.visibility = View.VISIBLE
            } else {
                nextBtn.visibility = View.VISIBLE
                submitBtn.visibility = View.GONE
            }

        }
    }

    override fun onClick(view: View?) {
        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))
        }

        val clickedBtn = view as Button
        when (view?.id) {
            R.id.next_btn -> {
                // Next button clicked
                loadNextQuestion()
            }
            R.id.previous_btn -> {
                // Previous button clicked
                loadPreviousQuestion()
            }
            R.id.submitBtn -> {
                // Previous button clicked
                finishQuiz()
            }
            else -> {
                // Options button clicked
                selectedAns = clickedBtn.text.toString()
                clickedBtn.setBackgroundColor(getColor(R.color.orange))
            }
        }
    }

    private fun loadPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            // Save the selected answer for the current question
            selectedAnswers[currentQuestionIndex] = selectedAns
            currentQuestionIndex--
            // Load the selected answer for the previous question
            selectedAns = selectedAnswers[currentQuestionIndex]
            loadQuestions()
        } else {
            selectedAns = selectedAnswers[currentQuestionIndex]
            loadQuestions()
        }
    }

    private fun loadNextQuestion() {
        // Save the selected answer for the current question
        selectedAnswers[currentQuestionIndex] = selectedAns
        // Check if the selected answer is correct
        if (selectedAns == questionModelList[currentQuestionIndex].correct) {
            score++
            Log.i("Score of Quiz", score.toString())
        }

        // Check if it's the last question
        if (currentQuestionIndex == questionModelList.size - 1) {
            // If it's the last question, finish the quiz and show the dialog
            finishQuiz()
        } else {
            // Move to the next question
            currentQuestionIndex++
            // Load the selected answer for the next question
            selectedAns = selectedAnswers[currentQuestionIndex]
            loadQuestions()
        }
    }



//    private fun loadNextQuestion() {
//        // Save the selected answer for the current question
//        selectedAnswers[currentQuestionIndex] = selectedAns
//        // Check if the selected answer is correct
//        if (selectedAns == questionModelList[currentQuestionIndex].correct) {
//            score++
//            Log.i("Score of Quiz", score.toString())
//        }
//
//        // Move to the next question
//        currentQuestionIndex++
//
//        // Check if it's the last question
//        if (currentQuestionIndex == questionModelList.size) {
//            // If it's the last question, finish the quiz and show the dialog
//
//            finishQuiz()
//        } else {
//            // Load the selected answer for the next question
//            selectedAns = selectedAnswers[currentQuestionIndex]
//            loadQuestions()
//        }
//    }


    private fun finishQuiz() {
        // after questions finish the score dialog will display with result
        // Check if the selected answer for the last question is correct and increment score if necessary
        if (selectedAns == questionModelList.last().correct) {
            score++
        }

        val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()



        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage %"
            if (percentage > 60) {
                scoreTitle.text = "Congrats! You have passed"
                scoreTitle.setTextColor(Color.BLUE)
            } else {
                scoreTitle.text = "Oops! You have failed"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestions are correct"

            finishBtn.setOnClickListener {
                finish()
            }
        }
        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
    }
}




