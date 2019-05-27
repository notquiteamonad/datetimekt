import Consts.SECONDS_IN_AN_HOUR
import Consts.SECONDS_IN_A_MINUTE

class Duration constructor (
        totalSeconds: Int
): TimeType(totalSeconds, false) {

    constructor(h: Int, m: Int, s: Int): this(s + SECONDS_IN_A_MINUTE * m + SECONDS_IN_AN_HOUR * h)

    operator fun plus(other: TimeType): Duration = Duration(this.getHours() + other.getHours(), this.getMinutes() + other.getMinutes(), this.getSeconds() + other.getSeconds())
    operator fun minus(other: TimeType): Duration = Duration(this.getHours() - other.getHours(), this.getMinutes() - other.getMinutes(), this.getSeconds() - other.getSeconds())

    companion object

}