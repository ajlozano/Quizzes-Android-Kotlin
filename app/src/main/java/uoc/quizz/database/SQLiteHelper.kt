package uoc.quizz.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf
import kotlinx.coroutines.selects.select
import uoc.quizz.Question

class SQLiteHelper(context: Context, DATABASE_NAME: String?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "question.db"
        private const val COLUMN_NAME_TITLE = "title"
        private const val COLUMN_OPTION_A = "optionA"
        private const val COLUMN_OPTION_B = "optionB"
        private const val COLUMN_OPTION_C = "optionC"
        private const val COLUMN_RESULT = "result"
        private const val COLUMN_IMG = "image"
        private const val COLUMN_ATTEMPTS = "attempts"
    }



    override fun onCreate(db: SQLiteDatabase?) {

        val SQL_CREATE_ENTRIES =
            "CREATE TABLE TABLE_NAME (" +
                    "INTEGER PRIMARY KEY," +
                    "COLUMN_NAME_TITLE TEXT," +
                    "COLUMN_OPTION_A TEXT," +
                    "COLUMN_OPTION_B TEXT," +
                    "COLUMN_OPTION_C TEXT," +
                    "COLUMN_RESULT TEXT," +
                    "COLUMN_IMG IMAGE," +
                    "COLUMN_ATTEMPTS TEXT)"
        db?.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXIST $TABLE_NAME")
        onCreate(db)
    }

    fun insertQuestion(q: Question): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME_TITLE, q.title)
        contentValues.put(COLUMN_OPTION_A, q.options[0])
        contentValues.put(COLUMN_OPTION_B, q.options[1])
        contentValues.put(COLUMN_OPTION_C, q.options[2])
        contentValues.put(COLUMN_RESULT, q.result)
        contentValues.put(COLUMN_IMG, q.imgURL)
        contentValues.put(COLUMN_ATTEMPTS, q.attempts)

        val success = db.insert(COLUMN_NAME_TITLE, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllQuestions(): ArrayList<Question> {
        val qList: ArrayList<Question> = ArrayList()
        val selectQuery = "SELECT * FROM $COLUMN_NAME_TITLE"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var title: String
        var optionA: String
        var optionB: String
        var optionC: String
        var result: String
        var img: String
        var attempts: Int

        if (cursor.moveToFirst()) {
            do {
                title = cursor.getString(cursor.getColumnIndex("title"))
                optionA = cursor.getString(cursor.getColumnIndex("optionA"))
                optionB = cursor.getString(cursor.getColumnIndex("optionB"))
                optionC = cursor.getString(cursor.getColumnIndex("optionC"))
                result = cursor.getString(cursor.getColumnIndex("result"))
                img = cursor.getString(cursor.getColumnIndex("image"))
                attempts = cursor.getInt(cursor.getColumnIndex("attempts"))

                val q = Question(title, arrayOf(optionA, optionB, optionC), result, img, attempts)
                qList.add(q)
            } while (cursor.moveToNext())
        }
        return qList
    }
}
