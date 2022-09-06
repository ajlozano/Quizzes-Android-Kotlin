package uoc.quizz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import uoc.quizz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var questionNum = 0
    var questions = arrayListOf<Question>()
    var selectedOption:String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionManager()

        binding.questionNumber.text = (questionNum + 1).toString()
        binding.questionTotal.text = (QConstants.Q_TOTAL + 1).toString()
        binding.title.text = questions[questionNum].title
        binding.optionOne.text = questions[questionNum].options[0]
        binding.optionTwo.text = questions[questionNum].options[1]
        binding.optionThree.text = questions[questionNum].options[2]

        Picasso.get()
            .load(questions[questionNum].imgURL)
            .into(binding.image)

        selectedOption = questions[questionNum].options[0]

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedOption = when(checkedId){
                binding.optionOne.id -> binding.optionOne.text as String
                binding.optionTwo.id -> binding.optionTwo.text as String
                binding.optionThree.id -> binding.optionThree.text as String
                else -> "No option selected"
            }
        }

        binding.button.setOnClickListener { _ ->
            questions[questionNum].attempts ++

            if(selectedOption == questions[questionNum].result) {
                openResultActivity()
              }
            else {
                Toast.makeText(this, "Incorrect answer. Try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun questionManager(){
        if (questionsInitialized) {
            val bundle:Bundle = intent.extras!!
            questionNum = bundle.getInt(ResultActivity.QUESTION_NUM)
            questions = bundle.getParcelableArrayList<Question>(ResultActivity.QUESTION_KEY)!!
        }
        else if (!questionsInitialized){
            questionsInitialized = initQuestions()

        }
    }

    private fun openResultActivity () {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ResultActivity.QUESTION_KEY, questions)
        intent.putExtra(ResultActivity.QUESTION_NUM, questionNum)
        startActivity(intent)
        finish()
    }

    private fun initQuestions(): Boolean {
        questions.add(Question(getString(R.string.question1_title), arrayOf(QConstants.Q1_OPT_A, QConstants.Q1_OPT_B, QConstants.Q1_OPT_C), QConstants.Q1_OPT_C, QConstants.Q1_IMG_URL, 0))
        questions.add(Question(getString(R.string.question2_title), arrayOf(QConstants.Q2_OPT_A, QConstants.Q2_OPT_B, QConstants.Q2_OPT_C), QConstants.Q2_OPT_A, QConstants.Q2_IMG_URL, 0))
        questions.add(Question(getString(R.string.question3_title), arrayOf(QConstants.Q3_OPT_A, QConstants.Q3_OPT_B, QConstants.Q3_OPT_C), QConstants.Q3_OPT_B, QConstants.Q3_IMG_URL, 0))
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}


