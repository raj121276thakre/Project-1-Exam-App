package com.app.examapp


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.app.examapp.adapter.QuestionGridAdapter
import com.app.examapp.databinding.ActivityQuizExamBinding
import com.app.examapp.databinding.ScoreDialogBinding
import com.app.examapp.model.QuestionModel

class QuizExamActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityQuizExamBinding // declaration of binding

    private lateinit var drawerLayout: DrawerLayout

    //private var isQuestionBookmarkedList = MutableList<Boolean>(questionModelList.size) { false }
    private val bookmarkedQuestions = mutableListOf<Int>()

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


        drawerOpenClose()

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
        displayGridviewData()

        binding.bookmarkBtn.setOnClickListener {


            toggleBookmarkStatus(currentQuestionIndex)
            updateBookmarkButton()
            updateGridItemColor()
        }

        // Load the bookmark button's initial image
        updateBookmarkButton()
    }

    // book mark feature
    private fun displayGridviewData() {
        // Sample data for the GridView
        val questionNumbers = mutableListOf<String>()
        for (i in 1..questionModelList.size) {
            questionNumbers.add("$i")
        }

        // Find the GridView
        val gridView: GridView = findViewById(R.id.gridView)

        // Create and set the adapter
        val adapter = QuestionGridAdapter(this, questionNumbers, bookmarkedQuestions)
        gridView.adapter = adapter
    }

    private fun toggleBookmarkStatus(questionIndex: Int) {
        if (questionIndex in bookmarkedQuestions) {
            // If the question is already bookmarked, remove it from the list
            bookmarkedQuestions.remove(questionIndex)
        } else {
            // If the question is not bookmarked, add it to the list
            bookmarkedQuestions.add(questionIndex)
        }
    }

    private fun updateBookmarkButton() {
        val bookmarkButton: ImageButton = findViewById(R.id.bookmark_btn)

        if (currentQuestionIndex in bookmarkedQuestions) {
            // Change the bookmark button's image to bookmarked state
            bookmarkButton.setImageResource(R.drawable.bookmark_filled)
        } else {
            // Change the bookmark button's image to unbookmarked state
            bookmarkButton.setImageResource(R.drawable.bookmark2)
        }

        // Update the background tint color of the question number circle in the GridView
        val gridView: GridView = findViewById(R.id.gridView)
        for (i in 0 until gridView.childCount) {
            val view = gridView.getChildAt(i)
            if (view != null) {
                val questionNumberTextView: TextView = view.findViewById(R.id.question_number)
                val questionIndex = i
                if (questionIndex in bookmarkedQuestions) {
                    // Question is bookmarked, change the background color to pink
                    questionNumberTextView.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#F802EC"))
                } else {
                    // Question is not bookmarked, change the background color to gray
                    questionNumberTextView.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                }
            }
        }
    }

    // drawer open close
    private fun drawerOpenClose() {
        // Find the close_drawerbtn button within the included layout
        val closeDrawerButton: ImageButton = findViewById(R.id.close_drawerbtn)
        // Initialize the DrawerLayout
        drawerLayout = binding.drawerLayout
        // Set click listener for Drawer_btn to open/close drawer
        binding.DrawerBtn.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        // Set click listener for the close_drawerbtn to close the drawer
        closeDrawerButton.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }


    private fun updateGridItemColor() {
        val gridView: GridView = findViewById(R.id.gridView)
        val currentQuestion = questionModelList[currentQuestionIndex]
        val currentQuestionNumber = currentQuestion.index - 1 // Adjust index to start from 0

        for (i in 0 until gridView.childCount) {
            val view = gridView.getChildAt(i)
            if (view != null) {
                val questionNumberTextView: TextView = view.findViewById(R.id.question_number)
                val questionIndex = i

                val isAnswered = selectedAnswers[questionIndex] != null
                val isBookmarked = questionIndex in bookmarkedQuestions

                if (isBookmarked) {
                    // Question is bookmarked
                    questionNumberTextView.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#F802EC"))
                } else if (isAnswered) {
                    // User answered the question
                    questionNumberTextView.backgroundTintList =
                        ColorStateList.valueOf(Color.GREEN)
                } else if (questionIndex == currentQuestionNumber) {


                    // Current question
                    questionNumberTextView.backgroundTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow))


                } else {
                    // Unanswered question
                    questionNumberTextView.backgroundTintList =
                        ColorStateList.valueOf(Color.GRAY)
                }
            }
        }
    }


    //............all question related functions ..............
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
                updateGridItemColor()
            }

            R.id.previous_btn -> {
                // Previous button clicked
                loadPreviousQuestion()
                updateGridItemColor()
            }

            R.id.submitBtn -> {
                // Previous button clicked
                finishQuiz()
            }


            else -> {
                // Options button clicked
                selectedAns = clickedBtn.text.toString()
                clickedBtn.setBackgroundColor(getColor(R.color.orange))

                updateGridItemColor()


            }
        }


    }

    private fun loadQuestions() {

        // Check if it's the last question
        val isLastQuestion = currentQuestionIndex == questionModelList.size - 1

        if (currentQuestionIndex == questionModelList.size) {
            finishQuiz()
            return
        }
        val currentQuestion = questionModelList[currentQuestionIndex]

        binding.apply {
            //question number
            val questionNumber = currentQuestion.index
            //  questionIndicatorTextview.text = "Question $questionNumber / ${questionModelList.size}"

            // Update question index textview
            questionIndexTextview.text = "Q $questionNumber"
            questionIndicatorTextview.text =
                "Question ${currentQuestionIndex + 1}/ ${questionModelList.size}"
            //question progress
            questionProgressIndicator.progress =
                ((currentQuestionIndex + 1).toFloat() / questionModelList.size.toFloat() * 100).toInt()
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

        // Update bookmark button image for the current question
        updateBookmarkButton()

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
        updateGridItemColor()
    }

    private fun loadNextQuestion() {
        // Save the selected answer for the current question
        selectedAnswers[currentQuestionIndex] = selectedAns
        // Check if the selected answer is correct
        if (selectedAns == questionModelList[currentQuestionIndex].correct) {
            score++
            Log.i("Score of Quiz", score.toString())
        }


        // Change bookmark button image based on next question's bookmark status
        updateBookmarkButton()


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

            // Update bookmark button image for the next question
            updateBookmarkButton()
        }
        updateGridItemColor()
    }

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

        updateGridItemColor()
    }

    // start timer
    private fun startTimer() {
        val totalTimeInMills = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMills, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                //showing timer on timer indicator
                binding.timerIndicatorTextview.text =
                    String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                //finish the quiz
                finishQuiz()
            }
        }.start()
    }

}