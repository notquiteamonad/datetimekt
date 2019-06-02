import java.util.regex.Pattern

class DateTime(
        private var d: Date,
        private var t: Time
): Comparable<DateTime> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DateTime
        if (d != other.d) return false
        if (t != other.t) return false
        return true
    }

    override fun hashCode(): Int {
        var result = d.hashCode()
        result = 31 * result + t.hashCode()
        return result
    }

    override fun toString(): String = "$d@$t"

    fun toReadableString(): String = "${d.toReadableString()} $t"

    override fun compareTo(other: DateTime): Int =
            if (d == other.d) {
                t.compareTo(other.t)
            } else {
                d.compareTo(other.d)
            }

    companion object {

        private const val STRING_FORMAT_REGEX = """^\d{4}-\d{2}-\d{2}[@ ]\d{2}:\d{2}:\d{2}$"""

        /**
         * Gets the current date and time as a DateTime.
         */
        fun now(): DateTime = DateTime(Date.today(), Time.now())

        /**
         * Converts a string in the format yyyy-mm-dd@hh:mm:ss as would be given by
         * toString() into a Date.
         *
         * Returns null if the string is invalid.
         */
        fun fromString(string: String): DateTime? {
            val pattern = Pattern.compile(STRING_FORMAT_REGEX)
            return if (pattern.matcher(string).matches()) {
                val split = string.split(if (string.contains('@')) '@' else ' ')
                DateTime(Date.fromString(split[0])!!, Time.fromString(split[1])!!)
            } else {
                null
            }
        }

    }

}