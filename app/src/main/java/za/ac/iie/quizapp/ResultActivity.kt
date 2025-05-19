package za.ac.iie.quizapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import za.ac.iie.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val userName = intent.getStringExtra(Constants.USER_NAME)
        val score = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 5)

        val feedback = if (score >= 3) "Great job!" else "Keep practicing!"

        binding.tvName.text = userName
        binding.tvScore.text = "Your Score is $score out of $totalQuestions.\n$feedback"

        binding.btnFinish.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        binding.btnReview.setOnClickListener {
            val questions = Constants.getQuestions()
            val reviewText = questions.joinToString("\n\n") {
                "${it.question}\nAnswer: ${if (it.correctAnswer == 1) it.optionOne else it.optionTwo}"
            }

            AlertDialog.Builder(this)
                .setTitle("Review")
                .setMessage(reviewText)
                .setPositiveButton("OK", null)
                .show()
        }

        binding.btnExit.setOnClickListener {
            finishAffinity()
        }
    }
}
