import Consts.SECONDS_IN_AN_HOUR
import Consts.SECONDS_IN_A_MINUTE
import java.util.regex.Pattern

class Duration constructor (
        totalSeconds: Int
): TimeType(totalSeconds, false) {

    constructor(h: Int, m: Int, s: Int): this(s + SECONDS_IN_A_MINUTE * m + SECONDS_IN_AN_HOUR * h)

    operator fun plus(other: TimeType): Duration = Duration(this.getHours() + other.getHours(), this.getMinutes() + other.getMinutes(), this.getSeconds() + other.getSeconds())
    operator fun minus(other: TimeType): Duration = Duration(this.getHours() - other.getHours(), this.getMinutes() - other.getMinutes(), this.getSeconds() - other.getSeconds())

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

    }

}