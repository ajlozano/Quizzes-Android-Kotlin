package uoc.quizz.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
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

    fun insertQuestion(q: Question): Long {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(FeedEntry.QUESTION_ID, q.id)
            put(FeedEntry.NAME_TITLE, q.title)
            put(FeedEntry.OPTION_A, q.options[0])
            put(FeedEntry.OPTION_B, q.options[1])
            put(FeedEntry.OPTION_C, q.options[2])
            put(FeedEntry.RESULT, q.result)
            put(FeedEntry.IMAGE, q.imgURL)
            put(FeedEntry.ATTEMPTS, q.attempts)
        }

        //db.close()
        return db.insert(FeedEntry.TBL_QUESTION, null, values)
    }
    @SuppressLint("Range")
    fun readQuestionFromDb(id: Int) : ArrayList<Question> {
        val questions = ArrayList<Question>()
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + FeedEntry.TBL_QUESTION +
            " WHERE " + FeedEntry.QUESTION_ID + " ='" + id.toString() + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var title: String
        var optionA: String
        var optionB: String
        var optionC: String
        var result: String
        var image: String
        var attempts: Int

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                title = cursor.getString(cursor.getColumnIndex(FeedEntry.NAME_TITLE))
                optionA = cursor.getString(cursor.getColumnIndex(FeedEntry.OPTION_A))
                optionB = cursor.getString(cursor.getColumnIndex(FeedEntry.OPTION_B))
                optionC = cursor.getString(cursor.getColumnIndex(FeedEntry.OPTION_C))
                result = cursor.getString(cursor.getColumnIndex(FeedEntry.RESULT))
                image = cursor.getString(cursor.getColumnIndex(FeedEntry.IMAGE))
                attempts = cursor.getInt(cursor.getColumnIndex(FeedEntry.ATTEMPTS))
                questions.add(Question(id, title, arrayOf(optionA, optionB, optionC), result, image, attempts))
                cursor.moveToNext()
            }
        }
        return questions

        /*
        val db = this.readableDatabase
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(FeedEntry.QUESTION_ID,
            FeedEntry.NAME_TITLE,
            FeedEntry.OPTION_A,
            FeedEntry.OPTION_B,
            FeedEntry.OPTION_C,
            FeedEntry.RESULT,
            FeedEntry.IMAGE,
            FeedEntry.ATTEMPTS)

        // Filter results WHERE "title" = 'My Title'
        val selection = "${FeedEntry.NAME_TITLE} = ?"
        val selectionArgs = arrayOf("My title")
        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${FeedEntry.ATTEMPTS} DESC"

        val cursor = db.query(
            FeedEntry.TBL_QUESTION,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        Log.e("SQLite read Test", cursor.toString())

        val itemIds = mutableListOf<Long>()

        with(cursor) {
            do {
                val itemId = getLong(getColumnIndexOrThrow(FeedEntry.QUESTION_ID))
                itemIds.add(itemId)
                Log.e("SQLite read Test", itemId.toString())
            } while (moveToNext())
        }
        cursor.close()
        return itemIds
        */
    }
    @SuppressLint("Range")
    fun getAllQuestions(id: Int): ArrayList<Question> {
        val qList: ArrayList<Question> = ArrayList()
        val selectQuery = "SELECT * FROM $FeedReaderContract.FeedEntry.TBL_QUESTION"
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

                val q = Question(id, title, arrayOf(optionA, optionB, optionC), result, img, attempts)
                qList.add(q)
            } while (cursor.moveToNext())
        }
        return qList
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
