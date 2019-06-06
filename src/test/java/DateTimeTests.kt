import com.github.samueldple.datetimekt.Date
import com.github.samueldple.datetimekt.DateTime
import com.github.samueldple.datetimekt.Time
import io.kotlintest.data.forall
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class DateTimeTests: StringSpec ({

    "valid" {
        val date = Date(2000, 5, 1)
        val time = Time(5, 6, 7)
        val dt = DateTime(date, time)
        dt.getDate() shouldBe date
        dt.getTime() shouldBe time
    }

    "datetime to string" {
        forall(
                row("2000-05-01@08:30:00", "1 May 2000 08:30:00", 1, 5, 2000, 8, 30, 0),
                row("0050-01-01@08:30:00", "1 Jan 0050 08:30:00", 1, 1, 50, 8, 30, 0),
                row("9999-01-01@08:30:00", "1 Jan 9999 08:30:00", 1, 13, 10000, 8, 30, 0)
        ) { string, readableString, d, m, y, h, mi, s ->
            val date = Date(y, m, d)
            val time = Time(h, mi, s)
            val dt = DateTime(date, time)
            dt.toString() shouldBe string
            dt.toReadableString() shouldBe readableString
        }
    }

    "equality" {
        DateTime(Date(1, 2, 3), Time(1, 2, 3)) shouldBe
                DateTime(Date(1, 2, 3), Time(1, 2, 3))
    }

    "hashcode" {
        DateTime(Date(2000, 5, 6), Time(1, 2, 3)).hashCode() shouldBe 59588017
    }

    "comparison" {
        val dts = arrayOf(
                DateTime(Date(2000, 6, 5), Time(8, 30, 0)),
                DateTime(Date(2000, 6, 5), Time(8, 30, 0)),
                DateTime(Date(2000, 6, 5), Time(9, 0, 0)),
                DateTime(Date(2000, 6, 6), Time(8, 30, 0))
        )
        withClue("comparison1") {(dts[0] <= dts[1]).shouldBeTrue()}
        withClue("comparison2") {(dts[0] < dts[1]).shouldBeFalse()}
        withClue("comparison3") {(dts[0] >= dts[1]).shouldBeTrue()}
        withClue("comparison4") {(dts[0] < dts[2]).shouldBeTrue()}
        withClue("comparison5") {(dts[2] < dts[3]).shouldBeTrue()}
        withClue("comparison6") {(dts[3] > dts[1]).shouldBeTrue()}
    }

    "datetime from string" {
        val valid = DateTime(Date(2000, 5, 10), Time(8, 30, 0))
        DateTime.fromString("2000-05-10@08:30:00") shouldBe valid
        DateTime.fromString("2000-05-10 08:30:00") shouldBe valid
        DateTime.fromString("2-a11111@05:a:04") shouldBe null
    }

})