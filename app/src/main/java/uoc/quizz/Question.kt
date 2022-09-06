package uoc.quizz

import android.os.Parcelable
import android.widget.ImageView
import kotlinx.parcelize.Parcelize


object QConstants {
    const val Q1_TITLE = "Who is the author of this painting?"
    const val Q1_OPT_A = "Pablo Picasso"
    const val Q1_OPT_B = "Salvador Dalí"
    const val Q1_OPT_C = "Diego Velazquez"
    const val Q1_IMG_URL = "https://upload.wikimedia.org/wikipedia/commons/9/99/Las_Meninas_01.jpg"

    const val Q2_TITLE = "Who is he?"
    const val Q2_OPT_A = "Nikola Tesla"
    const val Q2_OPT_B = "Mark Twain"
    const val Q2_OPT_C = "Thomas Alva Edison"
    const val Q2_IMG_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/Tesla_circa_1890.jpeg/800px-Tesla_circa_1890.jpeg"

    const val Q3_TITLE = "What country does this flag belong to?"
    const val Q3_OPT_A = "El Salvador"
    const val Q3_OPT_B = "Nicaragua"
    const val Q3_OPT_C = "Uruguay"
    const val Q3_IMG_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/19/Flag_of_Nicaragua.svg/1920px-Flag_of_Nicaragua.svg.png"

    const val Q_TOTAL = 2
}

@Parcelize
class Question (val title:String, val options:Array<String>, val result:String, val imgURL:String, var attempts:Int) : Parcelable