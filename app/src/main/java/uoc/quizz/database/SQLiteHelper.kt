package uoc.quizz.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import uoc.quizz.Question
import uoc.quizz.database.FeedReaderContract.FeedEntry

object FeedReaderContract{
    object FeedEntry : BaseColumns {
        const val TBL_QUESTION = "tblquestion"
        const val QUESTION_ID = "questionid"
        const val NAME_TITLE = "title"
        const val OPTION_A = "optiona"
        const val OPTION_B = "optionb"
        const val OPTION_C = "optionc"
        const val RESULT = "result"
        const val IMAGE = "image"
        const val ATTEMPTS = "attempts"
    }
}

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        //db!!.execSQL("DROP TABLE IF EXISTS $TBL_QUESTION")
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertQuestionToDb(q: Question): Long {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(FeedEntry.QUESTION_ID, q.id)
            put(FeedEntry.NAME_TITLE, q.title)
            put(FeedEntry.OPTION_A, q.options[0])
            put(FeedEntry.OPTION_B, q.options[1])
            put(FeedEntry.OPTION_C, q.options[2])
            put(FeedEntry.RESULT, q.GetCorrectResponse())
            put(FeedEntry.IMAGE, q.imgURL)
            put(FeedEntry.ATTEMPTS, q.attempts)
        }
        //db.close()
        return db.insert(FeedEntry.TBL_QUESTION, null, values)
    }
    @SuppressLint("Range")
    fun readQuestionFromDb(id: Int) : Question {
        var question = Question(0,"", arrayOf("", "", ""), "", 0)
        val db = this.writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from " + FeedEntry.TBL_QUESTION +
            " WHERE " + FeedEntry.QUESTION_ID + " ='" + id.toString() + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return question
        }
        val title: String
        val optionA: String
        val optionB: String
        val optionC: String
        //val result: String
        val image: String
        val attempts: Int

        if (cursor!!.moveToFirst()) {
            title = cursor.getString(cursor.getColumnIndex(FeedEntry.NAME_TITLE))
            optionA = cursor.getString(cursor.getColumnIndex(FeedEntry.OPTION_A))
            optionB = cursor.getString(cursor.getColumnIndex(FeedEntry.OPTION_B))
            optionC = cursor.getString(cursor.getColumnIndex(FeedEntry.OPTION_C))
            //result = cursor.getString(cursor.getColumnIndex(FeedEntry.RESULT))
            image = cursor.getString(cursor.getColumnIndex(FeedEntry.IMAGE))
            attempts = cursor.getInt(cursor.getColumnIndex(FeedEntry.ATTEMPTS))
            question = Question(id, title, arrayOf(optionA, optionB, optionC), image, attempts)
        }
        return question
    }
    @SuppressLint("Range")
    fun readAllQuestionsFromDb(): ArrayList<Question> {
        val questions = ArrayList<Question>()
        val db = this.writableDatabase
        val cursor: Cursor?
        val projection = arrayOf(FeedEntry.QUESTION_ID, FeedEntry.NAME_TITLE, FeedEntry.OPTION_A, FeedEntry.OPTION_B,
        FeedEntry.OPTION_C, FeedEntry.RESULT, FeedEntry.IMAGE, FeedEntry.ATTEMPTS)

        try {
            cursor = db.query(
                FeedEntry.TBL_QUESTION, projection, null, null, null, null,
                FeedEntry.QUESTION_ID, null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var id: Int
        var title: String
        var optionA: String
        var optionB: String
        var optionC: String
        //var result: String
        var image: String
        var attempts: Int

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getInt(cursor.getColumnIndex(FeedEntry.QUESTION_ID))
                title = cursor.getString(cursor.getColumnIndex(FeedEntry.NAME_TITLE))
                optionA = cursor.getString(cursor.getColumnIndex(FeedEntry.OPTION_A))
                optionB = cursor.getString(cursor.getColumnIndex(FeedEntry.OPTION_B))
                optionC = cursor.getString(cursor.getColumnIndex(FeedEntry.OPTION_C))
                //result = cursor.getString(cursor.getColumnIndex(FeedEntry.RESULT))
                image = cursor.getString(cursor.getColumnIndex(FeedEntry.IMAGE))
                attempts = cursor.getInt(cursor.getColumnIndex(FeedEntry.ATTEMPTS))
                questions.add(Question(id, title, arrayOf(optionA, optionB, optionC), image, attempts))
                cursor.moveToNext()
            }
        }
        return questions
    }
    fun deleteQuestion(q: Question): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = FeedEntry.QUESTION_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(q.id.toString())
        // Issue SQL statement.
        db.delete(FeedEntry.TBL_QUESTION, selection, selectionArgs)
        return true
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "question.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${FeedEntry.TBL_QUESTION} (" +
                    "${FeedEntry.QUESTION_ID} INTEGER PRIMARY KEY, " +
                    "${FeedEntry.NAME_TITLE} TEXT, " +
                    "${FeedEntry.OPTION_A} TEXT, " +
                    "${FeedEntry.OPTION_B} TEXT, " +
                    "${FeedEntry.OPTION_C} TEXT, " +
                    "${FeedEntry.RESULT} TEXT, " +
                    "${FeedEntry.IMAGE} TEXT, " +
                    "${FeedEntry.ATTEMPTS} INTEGER)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TBL_QUESTION}"
    }
}
