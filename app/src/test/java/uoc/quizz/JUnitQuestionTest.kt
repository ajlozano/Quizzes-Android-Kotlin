package uoc.quizz

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class JUnitQuestionTest {
    @Test
    fun question_isCorrect() {
        var questions = arrayListOf<Question>()
        questions.add(Question(0, QConstants.Q1_TITLE, arrayOf(QConstants.Q1_OPT_A, QConstants.Q1_OPT_B, QConstants.Q1_OPT_C), QConstants.Q1_IMG_URL, 0))
        questions.add(Question(1, QConstants.Q2_TITLE, arrayOf(QConstants.Q2_OPT_A, QConstants.Q2_OPT_B, QConstants.Q2_OPT_C), QConstants.Q2_IMG_URL, 0))
        questions.add(Question(2, QConstants.Q3_TITLE, arrayOf(QConstants.Q3_OPT_A, QConstants.Q3_OPT_B, QConstants.Q3_OPT_C), QConstants.Q3_IMG_URL, 0))

        assertEquals(QConstants.Q1_RESULT, questions[0].GetCorrectResponse())
        assertEquals(QConstants.Q2_RESULT, questions[1].GetCorrectResponse())
        assertEquals(QConstants.Q3_RESULT, questions[2].GetCorrectResponse())
    }
}