import Consts.MAX_YEAR
import Consts.MONTHS_IN_A_YEAR
import java.time.LocalDate
import java.util.regex.Pattern

/**
 * Represents a month of a year between Jan 0000 and Dec 9999 inclusive.
 *
 * If m < 0 or m > 12, 12 will be added or subtracted respectively until it is in the valid range.
 *
 * If y < 0 or y > 9999, it will be set to 0 or 9999 respectively.
 */
class Month(
        m: Int,
        y: Int
): Comparable<Month> {

    private var m: Int
    private var y: Int

    init {
        var currentMonthValue = m
        var currentYearValue = y
        while (currentMonthValue <= 0) currentMonthValue += MONTHS_IN_A_YEAR
        while (currentMonthValue > 12) currentMonthValue -= MONTHS_IN_A_YEAR
        if (currentYearValue < 0) currentYearValue = 0
        if (currentYearValue > MAX_YEAR) currentYearValue = MAX_YEAR
        this.m = currentMonthValue
        this.y = currentYearValue
    }

    fun getMonth(): Int = m
    fun getYear(): Int = y

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Month
        if (m != other.m) return false
        if (y != other.y) return false
        return true
    }

    override fun hashCode(): Int = 31 * m + y

    override fun toString(): String = String.format("%04d-%02d", y, m)

    /**
     * Returns a String in the format Jan 2019
     */
    fun toReadableString(): String = String.format("%s %04d", MONTH_STRINGS[m - 1], y)

    override fun compareTo(other: Month): Int =
            (y * MONTHS_IN_A_YEAR + m).compareTo(other.getYear() * MONTHS_IN_A_YEAR + other.getMonth())

    /**
     * Returns the next month that comes in sequence from this one.
     *
     * If this one is Dec 9999, it will be returned unchanged.
     */
    fun nextMonth(): Month =
            if (y == MAX_YEAR && m == MONTHS_IN_A_YEAR) {
                this
            } else if (m == MONTHS_IN_A_YEAR) {
                Month(1, y+1)
            } else {
                Month(m+1, y)
            }

    /**
     * Returns the previous month that comes in sequence from this one.
     *
     * If this one is Jan 0000, it will be returned unchanged.
     */
    fun previousMonth(): Month =
            if (y == 0 && m == 1) {
                this
            } else if (m == 1) {
                Month(12, y-1)
            } else {
                Month(m-1, y)
            }

    /**
     * Adds `months` months to this Month chronologically.
     */
    fun addMonths(months: Int) {
        var newMonth = this
        repeat(months) { newMonth = newMonth.nextMonth() }
        modifyValues(newMonth)
    }

    /**
     * Subtracts `months` months from this Month chronologically.
     */
    fun subtractMonths(months: Int) {
        var newMonth = this
        repeat(months) { newMonth = newMonth.previousMonth() }
        modifyValues(newMonth)
    }

    /**
     * Sets this.m and this.y to newMonth's values.
     */
    private fun modifyValues(newMonth: Month) {
        this.m = newMonth.m
        this.y = newMonth.y
    }

    companion object {

        private const val VALID_FORMAT_REGEX = """^\d{4}-\d{2}$"""

        private val MONTH_STRINGS = arrayOf(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        /**
         * Gets the current month as a Month.
         */
        @JvmStatic
        @Suppress("unused")
        fun thisMonth(): Month {
            val date = LocalDate.now()
            return Month(date.monthValue, date.year)
        }

        /**
         * Converts a string in the format yyyy-mm as would be given by
         * toString() into a Month.
         *
         * Returns null if the string is invalid.
         */
        @JvmStatic
        fun fromString(string: String): Month? {
            val pattern = Pattern.compile(VALID_FORMAT_REGEX)
            return if (pattern.matcher(string).matches()) {
                val parts = string.split('-')
                Month(parts[1].toInt(), parts[0].toInt())
            } else {
                null
            }
        }

    }

}