package com.github.samueldple.datetimekt

import com.github.samueldple.datetimekt.Consts.SECONDS_IN_AN_HOUR
import com.github.samueldple.datetimekt.Consts.SECONDS_IN_A_MINUTE
import java.util.*
import java.util.regex.Pattern

/**
 * Contains a time of day, precise to the second.
 */
class Time constructor (
        totalSeconds: Int
): TimeType(totalSeconds.toLong(), true) {

    constructor(h: Int, m: Int, s: Int): this(s + SECONDS_IN_A_MINUTE * m + SECONDS_IN_AN_HOUR * h)

    operator fun plus(other: TimeType): Time = Time(this.getHours() + other.getHours(), this.getMinutes() + other.getMinutes(), this.getSeconds() + other.getSeconds())
    operator fun minus(other: TimeType): Time = Time(this.getHours() - other.getHours(), this.getMinutes() - other.getMinutes(), this.getSeconds() - other.getSeconds())

    companion object {

        private const val STRING_FORMAT_REGEX = """^\d{2}:\d{2}:\d{2}$"""

        /**
         * Converts a string in the format hh:mm:ss as would be given by
         * toString() into a Time.
         *
         * Returns null if the string is invalid.
         */
        @JvmStatic
        fun fromString(string: String): Time? {
            val pattern = Pattern.compile(STRING_FORMAT_REGEX)
            return if (pattern.matcher(string).matches()) {
                val parts = string.split(':')
                Time(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            } else {
                null
            }
        }

        /**
         * Gets the current time of day as a Time.
         */
        @JvmStatic
        @Suppress("unused")
        fun now(): Time {
            val currentTime = Calendar.getInstance()
            return Time(currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), currentTime.get(Calendar.SECOND))
        }

    }

}
