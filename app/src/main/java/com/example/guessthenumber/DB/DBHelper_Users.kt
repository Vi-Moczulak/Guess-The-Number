package com.example.guessthenumber.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.guessthenumber.data.message.message
import com.example.guessthenumber.data.model.LoggedInUser
import com.google.android.material.tabs.TabLayout

class DBHelper_Users(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    null, DATABASE_VER
) {
    companion object {
        private val DATABASE_VER = 2
        private val DATABASE_NAME = "EDMTDB.db"

        //Table
        private val TABLE_NAME = "Users"
        private val COL_displayName = "USERNAME"
        private val COL_password = "PASSWORD"
        private val COL_score = "SCORE"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME ($COL_displayName TEXT PRIMARY KEY, $COL_password TEXT,$COL_score INTEGER)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    val allMessage: MutableList<LoggedInUser>
        @SuppressLint("Range")
        get() {
            val listUser = ArrayList<LoggedInUser>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val message = LoggedInUser(
                        cursor.getString(cursor.getColumnIndex(COL_displayName)),
                        cursor.getString(cursor.getColumnIndex(COL_password))
                    )
                    listUser.add(message)
                } while (cursor.moveToNext())
            }
            db.close()
            return listUser
        }

    fun addUser(message: LoggedInUser) {
        val db = this.writableDatabase
        val values = ContentValues()
        //values.put(COL_ID, message.id_rank)
        values.put(COL_displayName, message.displayName)
        values.put(COL_password, message.password)
        values.put(COL_score,0)
        print("in add: " + message.displayName + message.password)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun validate(message: LoggedInUser): Boolean {
        val db = this.writableDatabase
        val user = message.displayName
        val pass = message.password
        val selectQuery =
            "SELECT * FROM $TABLE_NAME WHERE $COL_displayName='$user' AND $COL_password='$pass'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            db.close()
            return true
        }
        db.close()
        return false
    }

    fun validateUserName(message: LoggedInUser): Boolean {
        val db = this.writableDatabase
        val user = message.displayName
        val pass = message.password
        val selectQuery =
            "SELECT * FROM $TABLE_NAME WHERE $COL_displayName='$user'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            db.close()
            return true
        }
        db.close()
        return false
    }

    @SuppressLint("Range")
    fun getScore(user: String): Int {
        val db = this.writableDatabase
        var us = 0
        val selectQuery =
            "SELECT $COL_score FROM $TABLE_NAME WHERE $COL_displayName='$user'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            us = cursor.getInt(cursor.getColumnIndex(COL_score))
            db.close()
            return us
        }
        db.close()
        return 0
    }

    fun updateScore(user: String, score: Int) {
        val db = this.writableDatabase
        val selectQuery =
            "UPDATE $TABLE_NAME SET $COL_score=$score WHERE $COL_displayName='$user'"
        db.execSQL(selectQuery)
        db.close()
    }


}

