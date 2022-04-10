package com.example.guessthenumber.data

import android.app.Activity
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.guessthenumber.DB.DBHelper_Users
import com.example.guessthenumber.data.model.LoggedInUser
import com.example.guessthenumber.ui.login.LoginActivity
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(private val activity: LoginActivity) {

    private val shared = activity.getSharedPreferences("com.example.guessthenumber.shared", 0)

    fun login(username: String, password: String): Result<LoggedInUser> {
        val db = DBHelper_Users(activity.applicationContext)
        return try {
            val tmp = LoggedInUser(username, password)
            if (db.validate(tmp) and db.validateUserName(tmp)) {
                shared.edit()
                    .putString("username", tmp.displayName)
                    .putString("password", tmp.password)
                    .apply()
                Result.Success(tmp)
            } else {
                return Result.Error(IOException("Error logging in"))
            }

        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        } finally {
            db.close()
        }
    }

    fun signIn(username: String, password: String): Result<LoggedInUser> {

        val db = DBHelper_Users(activity.applicationContext)

        return try {
            val tmp = LoggedInUser(username, password)
            if (db.validate(tmp)) {
                Result.Error(IOException("Error registering in"))
            } else {
                db.addUser(tmp)
                Result.Success(tmp)
            }
        } catch (e: Throwable) {
            Result.Error(IOException("Error registering in", e))
        }
    }

    fun logout() {

        shared.edit()
            .remove("username")
            .remove("password")
            .apply()


    }
}


