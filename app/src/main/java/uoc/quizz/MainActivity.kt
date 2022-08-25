package uoc.quizz

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
    var questions = arrayListOf(
        Question(QConstants.Q1_TITLE, arrayOf(QConstants.Q1_OPT_A, QConstants.Q1_OPT_B, QConstants.Q1_OPT_C), QConstants.Q1_OPT_B, QConstants.Q1_IMG_URL),
        Question(QConstants.Q2_TITLE, arrayOf(QConstants.Q2_OPT_A, QConstants.Q2_OPT_B, QConstants.Q2_OPT_C), QConstants.Q2_OPT_B, QConstants.Q2_IMG_URL),
        Question(QConstants.Q3_TITLE, arrayOf(QConstants.Q3_OPT_A, QConstants.Q3_OPT_B, QConstants.Q3_OPT_C), QConstants.Q3_OPT_B, QConstants.Q3_IMG_URL)
    )
    var selectedOption : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Picasso.get()
            .load(questions[questionNum].imgURL)
            .into(binding.image)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                binding.optionOne.id -> selectedOption = binding.optionOne.text as String
                binding.optionTwo.id -> selectedOption = binding.optionOne.text as String
                binding.optionThree.id -> selectedOption = binding.optionOne.text as String
                else -> selectedOption = "No option selected"
            }
        }

        binding.button.setOnClickListener(View.OnClickListener { _ ->
            Toast.makeText(this, "${binding.radioGroup.checkedRadioButtonId}", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, selectedOption, Toast.LENGTH_SHORT).show()
        })

    }
}


