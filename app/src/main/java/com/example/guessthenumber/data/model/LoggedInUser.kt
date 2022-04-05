package com.example.guessthenumber.data.model

import android.icu.number.IntegerWidth

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    var userId: String,
    var displayName: String,
    var password: String
)