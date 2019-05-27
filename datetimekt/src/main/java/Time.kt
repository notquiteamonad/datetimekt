import Consts.SECONDS_IN_AN_HOUR
import Consts.SECONDS_IN_A_DAY
import Consts.SECONDS_IN_A_MINUTE
import java.time.LocalTime
import java.util.regex.Pattern

class Time private constructor (
    private var h: Int,
    private var m: Int,
    private var s: Int
): TimeType(h, m, s) {

    operator fun plus(other: TimeType): Time = new(this.h + other.getHours(), this.m + other.getMinutes(), this.s + other.getSeconds())
    operator fun minus(other: TimeType): Time = new(this.h - other.getHours(), this.m - other.getMinutes(), this.s - other.getSeconds())

    companion object {

        private const val STRING_FORMAT_REGEX = """^\d{2}:\d{2}:\d{2}$"""

        /**
         * Produces a new Time.
         *
         * Times of 24 hours or greater and negative times
         * will wrap around 24 hours to always produce a positive time.
         *
         * The value is calculated from total number of seconds so a time
         * with a minute value of 90 would add an hour to the resulting time
         * and set the minutes to 30, for example.
         */
        @JvmStatic
        fun new(h: Int, m: Int, s: Int): Time {
            val totalSeconds = s + SECONDS_IN_A_MINUTE * m + SECONDS_IN_AN_HOUR * h
            return fromSeconds(totalSeconds)
        }

        /**
        * Same as `TimeTuple::new()` but takes the total number of seconds
        * as its argument and calculates the hours, minutes, and seconds
        * from that.
        */
        @JvmStatic
        @Suppress("MemberVisibilityCanBePrivate")
        fun fromSeconds(totalSeconds: Int): Time {
            var s = totalSeconds
            while (s < 0) s += SECONDS_IN_A_DAY
            while (s >= SECONDS_IN_A_DAY) s -= SECONDS_IN_A_DAY
            val h = s / SECONDS_IN_AN_HOUR
            s -= h * SECONDS_IN_AN_HOUR
            val m = s / SECONDS_IN_A_MINUTE
            s -= m * SECONDS_IN_A_MINUTE
            return Time(h, m, s)
        }

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
                new(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
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
            val currentTime = LocalTime.now()
            return new(currentTime.hour, currentTime.minute, currentTime.second)
        }

    }

}
