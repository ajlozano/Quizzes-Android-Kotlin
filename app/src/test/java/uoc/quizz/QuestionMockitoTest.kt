package uoc.quizz

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class QuestionMockitoTest {

    lateinit var question: Question

    @Before
    fun SetUpQuestion() {
        question = Mockito.mock(Question::class.java)
        Mockito.`when`(question.GetCorrectResponse()).thenReturn(QConstants.Q1_RESULT)
    }

    @Test
    fun question_isCorrect() {
        Assert.assertEquals(QConstants.Q1_RESULT, question.GetCorrectResponse())
    }
}

