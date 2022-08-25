package uoc.quizz

import android.widget.ImageView


public object QConstants {
    const val Q1_TITLE = "pregunta1"
    const val Q1_OPT_A = "A"
    const val Q1_OPT_B = "B"
    const val Q1_OPT_C = "C"
    const val Q1_IMG_URL = "https://upload.wikimedia.org/wikipedia/commons/e/ed/Scapula_ant_numbered.png"

    const val Q2_TITLE = ""
    const val Q2_OPT_A = ""
    const val Q2_OPT_B = ""
    const val Q2_OPT_C = ""
    const val Q2_IMG_URL = "https://upload.wikimedia.org/wikipedia/commons/e/ed/Scapula_ant_numbered.png"

    const val Q3_TITLE = ""
    const val Q3_OPT_A = ""
    const val Q3_OPT_B = ""
    const val Q3_OPT_C = ""
    const val Q3_IMG_URL = "https://upload.wikimedia.org/wikipedia/commons/e/ed/Scapula_ant_numbered.png"

}

public class Question (val title:String, val options:Array<String>, val result:String, val imgURL:String) {

}