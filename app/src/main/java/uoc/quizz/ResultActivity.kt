package uoc.quizz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uoc.quizz.databinding.ActivityMainBinding
import uoc.quizz.databinding.ActivityResultBinding


public class ResultActivity : AppCompatActivity() {
    companion object{
        const val QUESTION_KEY = "Question"
        const val QUESTION_NUM = "QuestionNumber"
        const val TOTAL_QUESTIONS = "TotalQuestions"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle:Bundle = intent.extras!!
        val question = bundle.getParcelable<Question>(QUESTION_KEY)
        val questionNum = bundle.getInt(QUESTION_NUM)
        val totalQuestions = bundle.getInt(TOTAL_QUESTIONS)

        if (questionNum < totalQuestions) {

        }
        else {

        }

        binding.button.setOnClickListener { _ ->

        }
    }
}