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

        val userName = intent.getStringExtra("USER_NAME")
        val score = intent.getIntExtra("SCORE", 0)
        val questions = intent.getStringArrayExtra("QUESTIONS") ?: arrayOf()
        val answers = intent.getBooleanArrayExtra("ANSWERS") ?: booleanArrayOf()

        val feedback = if (score >= 3) "Great job!" else "Keep practicing!"

        binding.tvName.text = userName
        binding.tvScore.text = "Your Score is $score out of ${questions.size}.\n$feedback"

        binding.btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnReview.setOnClickListener {
            val reviewText = questions.indices.joinToString("\n\n") {
                "${questions[it]}\nAnswer: ${if (answers[it]) "True" else "False"}"
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
