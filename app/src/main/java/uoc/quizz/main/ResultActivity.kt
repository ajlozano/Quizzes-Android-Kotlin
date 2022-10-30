package uoc.quizz.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uoc.quizz.QConstants
import uoc.quizz.Question
import uoc.quizz.R
import uoc.quizz.database.SQLiteHelper
import uoc.quizz.databinding.ActivityResultBinding


public class ResultActivity : AppCompatActivity() {
    companion object{
        const val QUESTION_KEY = "Question"
        const val QUESTION_NUM_KEY = "QuestionNumber"
        const val SELECTED_OPTION_KEY = "SelectedAnswer"
    }
    private lateinit var sqliteHelper: SQLiteHelper

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle:Bundle = intent.extras!!
        val selectedAnswer = bundle.getString(SELECTED_OPTION_KEY)
        var numberOfQuestion = bundle.getInt(QUESTION_NUM_KEY)

        sqliteHelper = SQLiteHelper(this)
        var questionsObject = sqliteHelper.readAllQuestionsFromDb()

        numberOfQuestion = questionsAndBindingManager(binding, selectedAnswer, numberOfQuestion, questionsObject)

        binding.button.setOnClickListener { _ ->
            openMainActivity(numberOfQuestion)
        }
    }

    private fun questionsAndBindingManager(binding: ActivityResultBinding ,answer: String?, qNum: Int, q: ArrayList<Question>) : Int {
        var qNumber = qNum
        if(answer == q[qNumber].GetCorrectResponse()){
            if (qNumber < QConstants.Q_TOTAL) {
                binding.resultText.setText(getString(R.string.is_correct))
                binding.attemptsText.setText(getString(R.string.question_attempts))
                binding.button.setText(getString(R.string.next_button))
                binding.attempts.setText(q[qNumber].attempts.toString())
                binding.image.setImageDrawable(resources.getDrawable(R.drawable.check_symbol))
                qNumber++
            }
            else {
                binding.resultText.setText(getString(R.string.congratulations))
                binding.attemptsText.setText(getString(R.string.total_attempts))
                binding.button.setText(getString(R.string.finish_button))
                binding.image.setImageDrawable(resources.getDrawable(R.drawable.winner_symbol))

                var attempts = 0
                for (qtn in q) {
                    attempts += qtn.attempts
                    qtn.attempts = 0
                }

                binding.attempts.setText(attempts.toString())
                qNumber = 0

                //Reset all rows from Database
                for (qtn in q ) {
                    sqliteHelper.deleteQuestion(qtn)
                    sqliteHelper.insertQuestionToDb(qtn)
                }
            }
        }
        else {
            //Toast.makeText(this, questionsObject[numberOfQuestion].attempts, Toast.LENGTH_SHORT).show()
            binding.resultText.setText(getString(R.string.wrong_answer))
            binding.button.setText(getString(R.string.retry_button))
            binding.image.setImageDrawable(resources.getDrawable(R.drawable.error_symbol))
        }
        return qNumber
    }
    private fun openMainActivity(numberOfQuestion: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(QUESTION_NUM_KEY, numberOfQuestion)
        startActivity(intent)
    }
}