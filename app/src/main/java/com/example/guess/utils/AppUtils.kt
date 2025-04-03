package com.example.guess.utils

object AppUtils {

    fun generateUserId(): String{
        return generateUniqueId(8, ('A'..'Z') + ('a'..'z') + ('0'..'9'))
    }

    fun generateGroupId(): String {
        return generateUniqueId(6, ('a'..'z') + ('0'..'9'))
    }


    private fun generateUniqueId(length: Int, allowedChars: List<Char>): String{
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}