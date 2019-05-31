import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class DateTimeTests: StringSpec ({

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

})