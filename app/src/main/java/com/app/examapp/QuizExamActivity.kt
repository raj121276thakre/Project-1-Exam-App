package com.app.examapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.app.examapp.databinding.ActivityQuizExamBinding
import com.app.examapp.databinding.ScoreDialogBinding
import com.app.examapp.model.QuestionModel


class QuizExamActivity : AppCompatActivity() , View.OnClickListener{

    lateinit var binding: ActivityQuizExamBinding // declaration of binding

    companion object{
        var questionModelList : List<QuestionModel> = listOf()
        var time: String =""
    }

    var currentQuestionIndex = 0
    var selectedAns =""
    var score = 0

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
        }

        loadQuestions()
        startTimer()


    } // below are the functions

    private fun startTimer() {
        val totalTimeInMills = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMills, 1000L){
            override fun onTick(millisUntilFinished: Long) {
               val seconds = millisUntilFinished/1000
               val minutes = seconds/60
               val remainingSeconds = seconds % 60
                //showing timer on timer indicator
                binding.timerIndicatorTextview.text = String.format("%02d:%02d",minutes,remainingSeconds)
            }

            override fun onFinish() {
                //finish the quiz
            }

        }.start()
    }

    private fun loadQuestions(){
        selectedAns=""
        if (currentQuestionIndex == questionModelList.size){

            finishQuiz()
            return
        }


    binding.apply {
        //question number
        questionIndicatorTextview.text = "Question ${currentQuestionIndex+1}/ ${questionModelList.size}"
        //question progresss
        questionProgressIndicator.progress = ( currentQuestionIndex.toFloat()/ questionModelList.size.toFloat() * 100).toInt()
        //question title
        questionTextview.text = questionModelList[currentQuestionIndex].question
        //options
        btn0.text = questionModelList[currentQuestionIndex].options[0]
        btn1.text = questionModelList[currentQuestionIndex].options[1]
        btn2.text = questionModelList[currentQuestionIndex].options[2]
        btn3.text = questionModelList[currentQuestionIndex].options[3]
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

        if(clickedBtn.id == R.id.next_btn){
            //next btn is clicked
            if (selectedAns == questionModelList[currentQuestionIndex].correct){
                score++
                Log.i("Score of Quiz", score.toString())
            }
            currentQuestionIndex++
            loadQuestions()

        }else{
            //options btn is clicked
            selectedAns = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.orange))

        }
    }


    private fun finishQuiz(){
        // after questions finish the score dialog will display with result
        val totalQuestions = questionModelList.size
        val percentage = (( score.toFloat() / totalQuestions.toFloat()) * 100 ).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage %"
            if (percentage>60){
                scoreTitle.text = "Congrats! You have passed"
                scoreTitle.setTextColor(Color.BLUE)

            }else{
                scoreTitle.text = "Oops! You have failed"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestions are correct"

            finishBtn.setOnClickListener{
                finish()
            }

        }
        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()





    }




}






















