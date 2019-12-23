package com.github.samueldple.datetimekt

import com.github.samueldple.datetimekt.Consts.SECONDS_IN_AN_HOUR
import com.github.samueldple.datetimekt.Consts.SECONDS_IN_A_MINUTE
import java.util.regex.Pattern

/**
 * Contains a duration in hours, minutes, and seconds.
 */
class Duration constructor (
        totalSeconds: Long
): TimeType(totalSeconds, false) {

    constructor(h: Int, m: Int, s: Int): this(s.toLong() + (SECONDS_IN_A_MINUTE * m).toLong() + (SECONDS_IN_AN_HOUR * h).toLong())

    @Deprecated(
            "It is now recommended to use a Long instead to prevent overflows.",
            ReplaceWith("Duration(totalSeconds.toLong())", "com.github.samueldple.datetimekt.Duration"),
            DeprecationLevel.WARNING
    )
    constructor(totalSeconds: Int): this(totalSeconds.toLong())

    operator fun plus(other: TimeType): Duration = Duration(this.getHours() + other.getHours(), this.getMinutes() + other.getMinutes(), this.getSeconds() + other.getSeconds())
    operator fun minus(other: TimeType): Duration = Duration(this.getHours() - other.getHours(), this.getMinutes() - other.getMinutes(), this.getSeconds() - other.getSeconds())

    /**
     * Gets the number of days in this duration, ignoring
     * hours, minutes, and seconds.
     */
    fun toDays(): Int = this.getHours() / 24

    companion object {

        private const val STRING_FORMAT_REGEX = """^\d+:\d{2}:\d{2}$"""

        /**
         * Converts a string in the format h:mm:ss as would be given by
         * toString() into a Duration.
         *
         * The `h` portion of the string can have any non-zero length.
         *
         * Returns null if the string is invalid.
         */
        @JvmStatic
        fun fromString(string: String): Duration? {
            val pattern = Pattern.compile(STRING_FORMAT_REGEX)
            return if (pattern.matcher(string).matches()) {
                val parts = string.split(':')
                Duration(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            } else {
                null
            }
        }

        /**
         * Gets the duration between 2 DateTimes.
         */
        @JvmStatic
        fun between(dt1: DateTime, dt2: DateTime): Duration {
            if (dt1 == dt2) return Duration(0L)
            val smaller: DateTime; val greater: DateTime
            if (dt1 > dt2) {
                smaller = dt2; greater = dt1
            } else {
                smaller = dt1; greater = dt2
            }
            val daysBetween = greater.getDate().toDays() - smaller.getDate().toDays()
            val timeBetween = if (daysBetween == 0) {
                greater.getTime().toDuration() - smaller.getTime().toDuration()
            } else {
                greater.getTime().toDuration() + (Duration(24, 0, 0) - smaller.getTime().toDuration())
            }
            return timeBetween + Duration(24 * (daysBetween - 1), 0, 0)
        }

    }

}