import com.github.samueldple.datetimekt.Date
import com.github.samueldple.datetimekt.Month
import io.kotlintest.data.forall
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.withClue
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class MonthTests: StringSpec ({

    "months valid and invalid" {
        forall(
                row(2000, 12, 2000, 12),
                row(2000, 1, 2000, 13),
                row(9999, 5, 10000, 5)
        ) { expectedY, expectedM, inputY, inputM ->
            val month = Month(inputY, inputM)
            month.getMonth() shouldBe expectedM
            month.getYear() shouldBe expectedY
        }
    }

    "month to string" {
        forall(
                row("2000-05", "May 2000", 5, 2000),
                row("0050-01", "Jan 0050", 1, 50),
                row("9999-01", "Jan 9999", 13, 10000)
        ) { string, readableString, m, y ->
            Month(y, m).toString() shouldBe string
            Month(y, m).toReadableString() shouldBe readableString
        }
    }

    "all values valid" {
        assertAll { m: Int, y: Int ->
            val month = Month(y, m)
            month.getYear() in 0..9999
            month.getMonth() in 1..12
        }
    }

    "equality" {
        Month(2000, 5) shouldBe Month(2000, 5)
    }

    "hashcode" {
        Month(2000, 5).hashCode() shouldBe 62005
    }

    "comparison" {
        val months = arrayOf(Month(2000, 5), Month(2000, 5), Month(2000, 6), Month(2001, 1))
        withClue("comparison1") {(months[0] <= months[1]).shouldBeTrue()}
        withClue("comparison2") {(months[0] < months[1]).shouldBeFalse()}
        withClue("comparison3") {(months[0] >= months[1]).shouldBeTrue()}
        withClue("comparison4") {(months[0] < months[2]).shouldBeTrue()}
        withClue("comparison5") {(months[2] < months[3]).shouldBeTrue()}
        withClue("comparison6") {(months[3] > months[1]).shouldBeTrue()}
    }

    "months from strings" {
        forall(
                row(Month(2000, 15), "2000-15"),
                row(null, "002-10"),
                row(null, "2000-5"),
                row(null, "200005"),
                row(Month(2000, 5), "2000-05")
        ) { expected, string ->
            Month.fromString(string) shouldBe expected
        }
    }

    "next and previous months" {
        forall(
                row(4, 2000, 6, 2000, 5, 2000),
                row(11, 2000, 1, 2001, 12, 2000),
                row(11, 9999, 12,  9999, 12, 9999),
                row(12, 1999, 2, 2000, 1, 2000),
                row(1, 0, 2, 0, 1, 0)
        ) { prevM, prevY, nextM, nextY, origM, origY ->
            val month = Month(origY, origM)
            month.previousMonth() shouldBe Month(prevY, prevM)
            month.nextMonth() shouldBe Month(nextY, nextM)
        }
    }

    "adding and subtracting months" {
        val month1 = Month(2000, 6)
        val month1Orig = Month(2000, 6)
        month1.addMonths(1)
        month1 shouldBe month1Orig.nextMonth()
        val month2 = Month(2000, 12)
        val month2Orig = Month(2000, 12)
        month2.addMonths(2)
        month2 shouldBe month2Orig.nextMonth().nextMonth()
        month1.subtractMonths(1)
        month2.subtractMonths(2)
        month1 shouldBe month1Orig
        month2 shouldBe month2Orig
    }

    "from date" {
        Month.fromDate(Date(7, 5, 6)) shouldBe Month(7, 5)
    }

})