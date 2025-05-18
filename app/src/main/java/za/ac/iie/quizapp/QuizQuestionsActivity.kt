package za.ac.iie.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import za.ac.iie.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizQuestionsBinding

    private val questions = arrayOf(
        "South Africa was settled by Europeans in the 17th century.",
        "Nelson Mandela became president in 1994.",
        "The first European settlers in South Africa were British.",
        "Nelson Mandela was the first democratically elected president of South Africa.",
        "The gold rush in South Africa occurred primarily in Durban."
    )

    private val answers = booleanArrayOf(true, true, false, true, false)

    private var currentIndex = 0
    private var score = 0
    private var answered = false
    private lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra("USER_NAME") ?: "User"
        displayQuestion()

        binding.tvOptionOne.setOnClickListener {
            checkAnswer(true)
        }

        binding.tvOptionTwo.setOnClickListener {
            checkAnswer(false)
        }

        binding.btnNext.setOnClickListener {
            if (currentIndex < questions.size - 1) {
                currentIndex++
                displayQuestion()
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("USER_NAME", userName)
                intent.putExtra("SCORE", score)
                intent.putExtra("QUESTIONS", questions)
                intent.putExtra("ANSWERS", answers)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun displayQuestion() {
        binding.tvQuestion.text = questions[currentIndex]
        binding.tvFeedback.text = ""
        binding.progressBar.progress = currentIndex + 1
        binding.tvProgress.text = "${currentIndex + 1}/${questions.size}"
        answered = false
    }

    private fun checkAnswer(userAnswer: Boolean) {
        if (answered) return

        val correct = answers[currentIndex]
        if (userAnswer == correct) {
            binding.tvFeedback.text = "Correct!"
            score++
        } else {
            binding.tvFeedback.text = "Incorrect!"
        }

        answered = true
    }
}
