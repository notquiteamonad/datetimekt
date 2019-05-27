import Consts.MAX_YEAR
import Consts.MONTHS_IN_A_YEAR

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
) {

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

    fun getMonths(): Int = m
    fun getYears(): Int = y

    companion object {

        private val MONTH_STRINGS = arrayOf(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

    }

}