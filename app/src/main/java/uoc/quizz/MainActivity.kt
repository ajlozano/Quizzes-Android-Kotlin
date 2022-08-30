package uoc.quizz

import android.annotation.SuppressLint
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
    val questionTotal = 2
    val questions = arrayListOf(
        Question(QConstants.Q1_TITLE, arrayOf(QConstants.Q1_OPT_A, QConstants.Q1_OPT_B, QConstants.Q1_OPT_C), QConstants.Q1_OPT_C, QConstants.Q1_IMG_URL, 0),
        Question(QConstants.Q2_TITLE, arrayOf(QConstants.Q2_OPT_A, QConstants.Q2_OPT_B, QConstants.Q2_OPT_C), QConstants.Q2_OPT_A, QConstants.Q2_IMG_URL, 0),
        Question(QConstants.Q3_TITLE, arrayOf(QConstants.Q3_OPT_A, QConstants.Q3_OPT_B, QConstants.Q3_OPT_C), QConstants.Q3_OPT_B, QConstants.Q3_IMG_URL, 0)
    )
    var selectedOption : String = "1234"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var mainQuestion = questions[questionNum]
        selectedOption = questions[questionNum].options[0]

        binding.questionNumber.text = (questionNum + 1).toString()
        binding.questionTotal.text = (questionTotal + 1).toString()
        binding.title.text = questions[questionNum].title

        Picasso.get()
            .load(mainQuestion.imgURL)
            .into(binding.image)

        binding.optionOne.setText(questions[questionNum].options[0])
        binding.optionTwo.setText(questions[questionNum].options[1])
        binding.optionThree.setText(questions[questionNum].options[2])

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                binding.optionOne.id -> selectedOption = binding.optionOne.text as String
                binding.optionTwo.id -> selectedOption = binding.optionTwo.text as String
                binding.optionThree.id -> selectedOption = binding.optionThree.text as String
                else -> selectedOption = "No option selected"
            }
        }

        binding.button.setOnClickListener(View.OnClickListener { _ ->
            if(selectedOption == questions[questionNum].result) {
                Toast.makeText(this, selectedOption, Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Incorrect answer. Try again.", Toast.LENGTH_SHORT).show()
            }

            //Toast.makeText(this, "${binding.radioGroup.checkedRadioButtonId}", Toast.LENGTH_SHORT).show()
        })

    }
}


