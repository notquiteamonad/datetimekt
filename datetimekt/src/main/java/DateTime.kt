class DateTime(
        private var d: Date,
        private var t: Time
) {

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

}