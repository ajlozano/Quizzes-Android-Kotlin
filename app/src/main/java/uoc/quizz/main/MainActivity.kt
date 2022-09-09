package uoc.quizz.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import uoc.quizz.*
import uoc.quizz.database.SQLiteHelper
import uoc.quizz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var questionNum = 0
    var questions = arrayListOf<Question>()
    var selectedOption:String = ""
    private lateinit var sqliteHelper: SQLiteHelper

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqliteHelper = SQLiteHelper(this)
        questionManager()
        bindingManager(binding)
    }
    private fun questionManager(){
        if (questionsInitialized) {
            val bundle:Bundle = intent.extras!!
            questionNum = bundle.getInt(ResultActivity.QUESTION_NUM_KEY)
            questions = bundle.getParcelableArrayList<Question>(ResultActivity.QUESTION_KEY)!!
        }
        else if (!questionsInitialized){
            questionsInitialized = initQuestions()
        }
    }
    private fun initQuestions(): Boolean {
        questions.add(Question(0, getString(R.string.question1_title), arrayOf(QConstants.Q1_OPT_A, QConstants.Q1_OPT_B, QConstants.Q1_OPT_C), QConstants.Q1_OPT_C, QConstants.Q1_IMG_URL, 0))
        questions.add(Question(1, getString(R.string.question2_title), arrayOf(QConstants.Q2_OPT_A, QConstants.Q2_OPT_B, QConstants.Q2_OPT_C), QConstants.Q2_OPT_A, QConstants.Q2_IMG_URL, 0))
        questions.add(Question(2, getString(R.string.question3_title), arrayOf(QConstants.Q3_OPT_A, QConstants.Q3_OPT_B, QConstants.Q3_OPT_C), QConstants.Q3_OPT_B, QConstants.Q3_IMG_URL, 0))

        insertQuestionToDb(questions[0])
        insertQuestionToDb(questions[1])
        insertQuestionToDb(questions[2])
        //Read SQlite TEST
//        var questionTest = sqliteHelper.readQuestionFromDb(questions[0].id)
//        var questionTest2 = sqliteHelper.readQuestionFromDb(questions[1].id)
//        var questionTest3 = sqliteHelper.readQuestionFromDb(questions[2].id)

        return true
    }
    private fun bindingManager(binding: ActivityMainBinding) {
        binding.questionNumber.text = (questionNum + 1).toString()
        binding.questionTotal.text = (QConstants.Q_TOTAL + 1).toString()
        binding.title.text = questions[questionNum].title
        binding.optionOne.text = questions[questionNum].options[0]
        binding.optionTwo.text = questions[questionNum].options[1]
        binding.optionThree.text = questions[questionNum].options[2]
        Picasso.get()
            .load(questions[questionNum].imgURL)
            .into(binding.image)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedOption = when(checkedId){
                binding.optionOne.id -> binding.optionOne.text as String
                binding.optionTwo.id -> binding.optionTwo.text as String
                binding.optionThree.id -> binding.optionThree.text as String
                else -> {getString(R.string.no_option_selected) as String}
            }
        }
        binding.button.setOnClickListener { _ ->
            if(selectedOption != "") {
                questions[questionNum].attempts ++
                openResultActivity()
            }
            else {
                Toast.makeText(this, getString(R.string.no_option_selected), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun insertQuestionToDb(q: Question) {
        val status = sqliteHelper.insertQuestion(q)
        //Check insert success or not success
        if (status > -1) {
            Toast.makeText(this, "Added question...", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, "Record not saved...", Toast.LENGTH_SHORT).show()
        }
    }
    private fun openResultActivity () {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ResultActivity.QUESTION_KEY, questions)
        intent.putExtra(ResultActivity.SELECTED_OPTION_KEY, selectedOption)
        intent.putExtra(ResultActivity.QUESTION_NUM_KEY, questionNum)
        startActivity(intent)
        finish()
    }
    override fun onDestroy() {
        sqliteHelper.close()
        super.onDestroy()
    }
}


