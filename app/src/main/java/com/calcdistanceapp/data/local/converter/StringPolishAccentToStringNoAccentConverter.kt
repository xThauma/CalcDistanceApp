package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.domain.converter.Converter
import javax.inject.Inject

class StringPolishAccentToStringNoAccentConverter @Inject constructor(): Converter<String, String> {
    override fun convert(from: String): String = from.removeDiacriticsAndLowercase()

    private fun String.removeDiacriticsAndLowercase(): String {
        val originalChars = this.lowercase().toCharArray()
        val convertedChars = CharArray(originalChars.size)

        for (i in originalChars.indices) {
            convertedChars[i] = convertPolishCharToBase(originalChars[i])
        }

        return String(convertedChars)
    }

    private fun convertPolishCharToBase(letter: Char): Char {
        return when (letter) {
            'ą' -> 'a'
            'ć' -> 'c'
            'ę' -> 'e'
            'ł' -> 'l'
            'ń' -> 'n'
            'ó' -> 'o'
            'ś' -> 's'
            'ź', 'ż' -> 'z'
            else -> letter
        }
    }
}