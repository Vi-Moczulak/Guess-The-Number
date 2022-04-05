package com.example.guessthenumber.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.guessthenumber.data.message.message
import com.example.guessthenumber.data.model.LoggedInUser

class DBHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    null, DATABASE_VER
) {
    companion object {
        private val DATABASE_VER = 2
        private val DATABASE_NAME = "EDMTDB.db"

        //Table
        private val TABLE_NAME = "Rank"
        private val COL_ID = "ID"
        private val COL_displayName = "USERNAME"
        private val COL_score  = "SCORE"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_displayName TEXT, $COL_score TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    val allMessage:MutableList<message>
        @SuppressLint("Range")
        get(){
            val listUser = ArrayList<message>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor =  db.rawQuery(selectQuery, null)
            if(cursor.moveToFirst()){
                do {
                    val message = message(
                    cursor.getString(cursor.getColumnIndex(COL_ID)),
                     cursor.getString(cursor.getColumnIndex(COL_displayName)),
                     cursor.getString(cursor.getColumnIndex(COL_score))
                    )
                    listUser.add(message)
                } while (cursor.moveToNext())
            }
            db.close()
            return listUser
        }

    fun addMessage(message:message){
        val db= this.writableDatabase
        val values = ContentValues()
        //values.put(COL_ID, message.id_rank)
        values.put(COL_displayName, message.displayName)
        values.put(COL_score, message.score)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }


}


//"SELECT * FROM $TABLE_NAME WHERE $COL_USERNAME='$user' AND $COL_PASSWORD='$password'"

/*
fun testOne(key:String): Boolean {
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COL_KEY = '$key'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()){
            if(key == cursor.getString(cursor.getColumnIndex(COL_KEY))) {
                return false
            }
        }
        return true
    }
*/