package uoc.quizz.database

import androidx.room.*
import uoc.quizz.Question

@Dao
interface qDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(qList: MutableList<Question>)
    @Query("SELECT * FROM questions")
    fun getQuestions(): MutableList<Question>

    @Update
    fun addQuestion(): MutableList<Question>

    @Delete
    fun deleteQuestion(vararg q: Question)
}