import Consts.SECONDS_IN_AN_HOUR
import Consts.SECONDS_IN_A_DAY
import Consts.SECONDS_IN_A_MINUTE

class Duration constructor (
    private var h: Int,
    private var m: Int,
    private var s: Int
): TimeType(h, m, s) {

    operator fun plus(other: TimeType): Duration = Duration.new(this.h + other.getHours(), this.m + other.getMinutes(), this.s + other.getSeconds())
    operator fun minus(other: TimeType): Duration = Duration.new(this.h - other.getHours(), this.m - other.getMinutes(), this.s - other.getSeconds())

    companion object {

        /**
         * Produces a new Duration.
         *
         * Times do not wrap and can be any length.
         *
         * The value is calculated from total number of seconds so a time
         * with a minute value of 90 would add an hour to the resulting time
         * and set the minutes to 30, for example.
         */
        @JvmStatic
        fun new(h: Int, m: Int, s: Int): Duration {
            val totalSeconds = s + SECONDS_IN_A_MINUTE * m + SECONDS_IN_AN_HOUR * h
            return fromSeconds(totalSeconds)
        }

        /**
         * Same as `TimeTuple::new()` but takes the total number of seconds
         * as its argument and calculates the hours, minutes, and seconds
         * from that.
         */
        @JvmStatic
        fun fromSeconds(totalSeconds: Int): Duration {
            var s = totalSeconds
            while (s < 0) s += SECONDS_IN_A_DAY
            val h = s / SECONDS_IN_AN_HOUR
            s -= h * SECONDS_IN_AN_HOUR
            val m = s / SECONDS_IN_A_MINUTE
            s -= m * SECONDS_IN_A_MINUTE
            return Duration(h, m, s)
        }

    }

}