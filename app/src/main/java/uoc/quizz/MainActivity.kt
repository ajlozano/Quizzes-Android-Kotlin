package uoc.quizz

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import uoc.quizz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Picasso.get()
            .load("https://upload.wikimedia.org/wikipedia/commons/e/ed/Scapula_ant_numbered.png")
            .into(binding.image)
    }
}

