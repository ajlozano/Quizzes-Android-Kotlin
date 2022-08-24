package uoc.quizz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import uoc.quizz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Array of questions for the quizz
        //val questions = Array<Question>(5)
        //questions.set(0, Question("prueba", arrayOf("prueba1","prueba2", "prueba3"), "prueba", binding.image))

        Picasso.get()
            .load("https://upload.wikimedia.org/wikipedia/commons/e/ed/Scapula_ant_numbered.png")
            .into(binding.image)
    }
}

