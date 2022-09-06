package uoc.quizz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import uoc.quizz.databinding.ActivityResultBinding


public class ResultActivity : AppCompatActivity() {
    companion object{
        const val QUESTION_KEY = "Question"
        const val QUESTION_NUM = "QuestionNumber"
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle:Bundle = intent.extras!!
        val questions = bundle.getParcelableArrayList<Question>(QUESTION_KEY)!!
        var questionNum = bundle.getInt(QUESTION_NUM)

        if (questionNum < QConstants.Q_TOTAL) {
            binding.resultText.setText(getString(R.string.is_correct))
            binding.attemptsText.setText(getString(R.string.question_attempts))
            binding.button.setText(getString(R.string.next_button))
            binding.attempts.setText(questions[questionNum].attempts.toString())
            binding.image.setImageDrawable(resources.getDrawable(R.drawable.check_symbol))
            questionNum++
        }
        else {
            binding.resultText.setText(getString(R.string.congratulations))
            binding.attemptsText.setText(getString(R.string.total_attempts))
            binding.button.setText(getString(R.string.finish_button))
            binding.image.setImageDrawable(resources.getDrawable(R.drawable.winner_symbol))

            var attempts = 0
            for (q in questions) {
                attempts += q.attempts
            }

            binding.attempts.setText(attempts.toString())

        }

        binding.button.setOnClickListener { _ ->
            openMainActivity(questions, questionNum)
        }
    }

    private fun openMainActivity(questions: ArrayList<Question>, questionNum: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(QUESTION_KEY, questions)
        intent.putExtra(QUESTION_NUM, questionNum)
        startActivity(intent)
    }
}