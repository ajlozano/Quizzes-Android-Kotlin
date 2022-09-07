package uoc.quizz.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uoc.quizz.QConstants
import uoc.quizz.Question
import uoc.quizz.R
import uoc.quizz.databinding.ActivityResultBinding


public class ResultActivity : AppCompatActivity() {
    companion object{
        const val QUESTION_KEY = "Question"
        const val QUESTION_NUM_KEY = "QuestionNumber"
        const val SELECTED_OPTION_KEY = "SelectedAnswer"
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle:Bundle = intent.extras!!
        val questionsObject = bundle.getParcelableArrayList<Question>(QUESTION_KEY)!!
        val selectedAnswer = bundle.getString(SELECTED_OPTION_KEY)
        var numberOfQuestion = bundle.getInt(QUESTION_NUM_KEY)

        if(selectedAnswer == questionsObject[numberOfQuestion].result){
            if (numberOfQuestion < QConstants.Q_TOTAL) {
                binding.resultText.setText(getString(R.string.is_correct))
                binding.attemptsText.setText(getString(R.string.question_attempts))
                binding.button.setText(getString(R.string.next_button))
                binding.attempts.setText(questionsObject[numberOfQuestion].attempts.toString())
                binding.image.setImageDrawable(resources.getDrawable(R.drawable.check_symbol))
                numberOfQuestion++
            }
            else {
                binding.resultText.setText(getString(R.string.congratulations))
                binding.attemptsText.setText(getString(R.string.total_attempts))
                binding.button.setText(getString(R.string.finish_button))
                binding.image.setImageDrawable(resources.getDrawable(R.drawable.winner_symbol))

                var attempts = 0
                for (q in questionsObject) {
                    attempts += q.attempts
                    q.attempts = 0
                }


                binding.attempts.setText(attempts.toString())
                numberOfQuestion = 0
            }
        }
        else {
            //Toast.makeText(this, questionsObject[numberOfQuestion].attempts, Toast.LENGTH_SHORT).show()
            binding.resultText.setText(getString(R.string.wrong_answer))
            binding.button.setText(getString(R.string.retry_button))
            binding.image.setImageDrawable(resources.getDrawable(R.drawable.error_symbol))
        }

        binding.button.setOnClickListener { _ ->
            openMainActivity(questionsObject, numberOfQuestion)
        }
    }

    private fun openMainActivity(questionsObject: ArrayList<Question>, numberOfQuestion: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(QUESTION_KEY, questionsObject)
        intent.putExtra(QUESTION_NUM_KEY, numberOfQuestion)
        startActivity(intent)
    }
}