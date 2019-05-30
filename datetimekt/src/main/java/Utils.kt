internal object Utils {

    internal fun getLastDateInMonth(month: Int, year: Int): Int {
        return when (month) {
            2 -> if (isLeapYear(year)) 29 else 28
            in arrayOf(1, 3, 5, 7, 8, 10, 12) -> 31
            else -> 30
        }
    }

    internal fun isLeapYear(year: Int): Boolean = (year % 4 == 0 &&(year % 100 != 0 || year % 400 == 0))

}