package za.ac.iie.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()

        // Set your XML layout
        setContentView(R.layout.activity_quiz_questions)

        // Optional: Applies padding for system bars (if your layout has a root view with id 'main')
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get user's name
        mUserName = intent.getStringExtra(Constants.USER_NAME)

        // Load questions
        mQuestionsList = Constants.getQuestions()

        // Display first question
        setQuestion()

        // Set up option click listeners
        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> selectedOptionView(tv_option_one, 1)
            R.id.tv_option_two -> selectedOptionView(tv_option_two, 2)
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++
                    if (mCurrentPosition <= mQuestionsList!!.size) {
                        setQuestion()
                    } else {
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, mUserName)
                        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    btn_submit.text = if (mCurrentPosition == mQuestionsList!!.size) "FINISH" else "GO TO NEXT QUESTION"
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun setQuestion() {
        val question = mQuestionsList!![mCurrentPosition - 1]
        defaultOptionsView()

        btn_submit.text = if (mCurrentPosition == mQuestionsList!!.size) "FINISH" else "SUBMIT"

        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition/${progressBar.max}"

        tv_question.text = question.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
    }

    private fun defaultOptionsView() {
        val options = arrayListOf(tv_option_one, tv_option_two)
        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> tv_option_one.background = ContextCompat.getDrawable(this, drawableView)
            2 -> tv_option_two.background = ContextCompat.getDrawable(this, drawableView)
        }
    }
}
