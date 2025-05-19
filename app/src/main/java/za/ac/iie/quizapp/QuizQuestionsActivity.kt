package za.ac.iie.quizapp

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import za.ac.iie.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionsBinding
    private var currentPosition = 0
    private var selectedOption = 0
    private var score = 0
    private lateinit var userName: String
    private lateinit var questionsList: ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra(Constants.USER_NAME) ?: "User"
        questionsList = Constants.getQuestions()

        setQuestion()

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)

        binding.btnSubmit.setOnClickListener {
            if (selectedOption == 0) {
                binding.tvFeedback.apply {
                    text = "Please select an answer."
                    setTextColor(ContextCompat.getColor(this@QuizQuestionsActivity, android.R.color.black))
                    setTypeface(null, Typeface.NORMAL)
                }
                return@setOnClickListener
            }

            val question = questionsList[currentPosition]
            if (selectedOption == question.correctAnswer) {
                binding.tvFeedback.apply {
                    text = "Correct!"
                    setTextColor(ContextCompat.getColor(this@QuizQuestionsActivity, android.R.color.holo_green_dark))
                    setTypeface(null, Typeface.BOLD)
                }
                score++
            } else {
                binding.tvFeedback.apply {
                    text = "Incorrect!"
                    setTextColor(ContextCompat.getColor(this@QuizQuestionsActivity, android.R.color.holo_red_dark))
                    setTypeface(null, Typeface.BOLD)
                }
            }

            binding.btnSubmit.isEnabled = false
        }

        binding.btnNext.setOnClickListener {
            currentPosition++
            if (currentPosition < questionsList.size) {
                setQuestion()
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(Constants.USER_NAME, userName)
                intent.putExtra(Constants.CORRECT_ANSWERS, score)
                intent.putExtra(Constants.TOTAL_QUESTIONS, questionsList.size)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setQuestion() {
        val question = questionsList[currentPosition]

        binding.tvQuestion.text = question.question
        binding.ivImage.setImageResource(question.image)
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.progressBar.progress = currentPosition + 1
        binding.tvProgress.text = "${currentPosition + 1}/${questionsList.size}"

        selectedOption = 0
        defaultOptions()

        binding.tvFeedback.apply {
            text = ""
            setTextColor(ContextCompat.getColor(this@QuizQuestionsActivity, android.R.color.black))
            setTypeface(null, Typeface.NORMAL)
        }

        binding.btnSubmit.isEnabled = true
    }

    private fun defaultOptions() {
        val options = listOf(binding.tvOptionOne, binding.tvOptionTwo)
        for (option in options) {
            option.setBackgroundResource(R.drawable.default_option_border_bg)
            option.setTypeface(null, Typeface.NORMAL)
        }
    }

    override fun onClick(v: View?) {
        defaultOptions()
        when (v?.id) {
            R.id.tv_option_one -> {
                selectedOption = 1
                binding.tvOptionOne.setBackgroundResource(R.drawable.selected_option_border_bg)
                binding.tvOptionOne.setTypeface(null, Typeface.BOLD)
            }
            R.id.tv_option_two -> {
                selectedOption = 2
                binding.tvOptionTwo.setBackgroundResource(R.drawable.selected_option_border_bg)
                binding.tvOptionTwo.setTypeface(null, Typeface.BOLD)
            }
        }
    }
}
