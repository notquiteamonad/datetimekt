package com.github.samueldple.datetimekt

import com.github.samueldple.datetimekt.Consts.MINUTES_IN_AN_HOUR
import com.github.samueldple.datetimekt.Consts.SECONDS_IN_AN_HOUR
import com.github.samueldple.datetimekt.Consts.SECONDS_IN_A_DAY
import com.github.samueldple.datetimekt.Consts.SECONDS_IN_A_MINUTE

abstract class TimeType (
        totalSeconds: Long,
        wrap: Boolean
): Comparable<TimeType> {

    private var h: Int
    private var m: Int
    private var s: Int

    init {
        var currentTotalSeconds = totalSeconds
        while (currentTotalSeconds < 0) currentTotalSeconds += SECONDS_IN_A_DAY
        if (wrap) while (currentTotalSeconds >= SECONDS_IN_A_DAY) currentTotalSeconds -= SECONDS_IN_A_DAY
        h = (currentTotalSeconds / SECONDS_IN_AN_HOUR).toInt()
        currentTotalSeconds -= h * SECONDS_IN_AN_HOUR
        m = (currentTotalSeconds / SECONDS_IN_A_MINUTE).toInt()
        currentTotalSeconds -= m * SECONDS_IN_A_MINUTE
        s = currentTotalSeconds.toInt()
    }

    fun getHours(): Int = h
    fun getMinutes(): Int = m
    fun getSeconds(): Int = s

    fun toTime(): Time = Time(h, m, s)
    @Suppress("unused")
    fun toDuration(): Duration = Duration(h, m, s)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TimeType
        if (h != other.h) return false
        if (m != other.m) return false
        if (s != other.s) return false
        return true
    }

    override fun hashCode(): Int {
        var result = h
        result = 31 * result + m
        result = 31 * result + s
        return result
    }

    override fun toString(): String {
        return String.format("%02d:%02d:%02d", h, m, s)
    }

    override fun compareTo(other: TimeType): Int = this.toSeconds().compareTo(other.toSeconds())

    /**
     * Produces a string such as 08:30 for 8 hours and 30 minutes.
     *
     * Ignores seconds.
     */
    fun toHHMMString(): String {
        return String.format("%02d:%02d", h, m)
    }

    /**
     * Gets the total number of seconds in the time.
     */
    fun toSeconds(): Int = SECONDS_IN_AN_HOUR * h + SECONDS_IN_A_MINUTE * m + s

    /**
     * Gets the total number of minutes in the time, ignoring seconds.
     */
    fun toMinutes(): Int = MINUTES_IN_AN_HOUR * h + m

    companion object

}
