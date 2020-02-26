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

class DateTests: StringSpec ({

    "dates valid and invalid" {
        forall(
                row(2000, 12, 5, 2000, 12, 5),
                row(2000, 12, 1, 2000, 12, 0),
                row(2000, 12, 31, 2000, 12, 40),
                row(2000, 1, 5, 2000, 13, 5),
                row(9999, 5, 5, 10000, 5, 5)
        ) { expectedY, expectedM, expectedD, inputY, inputM, inputD ->
            val date = Date(inputY, inputM, inputD)
            date.getDate() shouldBe expectedD
            date.getMonth() shouldBe expectedM
            date.getYear() shouldBe expectedY
        }
    }

    "all values valid" {
        assertAll { d: Int, m: Int, y: Int ->
            val date = Date(y, m, d)
            date.getYear() in 0..9999
            date.getMonth() in 1..12
            date.getDate() in 0..31
        }
    }

    "date to string" {
        forall(
                row("2000-05-01", "1 May 2000", 1, 5, 2000),
                row("0050-01-01", "1 Jan 0050", 1, 1, 50),
                row("9999-01-01", "1 Jan 9999", 1, 13, 10000)
        ) { string, readableString, d, m, y ->
            Date(y, m, d).toString() shouldBe string
            Date(y, m, d).toReadableString() shouldBe readableString
        }
    }

    "equality" {
        Date(9, 5, 1) shouldBe Date(9, 5, 1)
    }

    "hashcode" {
        Date(2000, 5, 6).hashCode() shouldBe 1922161
    }

    "comparison" {
            val dates = arrayOf(
                    Date(2000, 6, 5),
                    Date(2000, 6, 5),
                    Date(2000, 7, 4),
                    Date(2001, 1, 1)
            )
            withClue("comparison1") {(dates[0] <= dates[1]).shouldBeTrue()}
            withClue("comparison2") {(dates[0] < dates[1]).shouldBeFalse()}
            withClue("comparison3") {(dates[0] >= dates[1]).shouldBeTrue()}
            withClue("comparison4") {(dates[0] < dates[2]).shouldBeTrue()}
            withClue("comparison5") {(dates[2] < dates[3]).shouldBeTrue()}
            withClue("comparison6") {(dates[3] > dates[1]).shouldBeTrue()}
    }

    "date from string" {
        forall(
                row(Date(2000, 6, 10), "2000-06-10"),
                row(Date(2000, 4, 10), "2000-16-10"),
                row(null, "20000610"),
                row(null, "20O0-06-10")
        ) { expected, string ->
            Date.fromString(string) shouldBe expected
        }
    }

    "to month" {
        Date(7, 6, 5).toMonth() shouldBe Month(7, 6)
    }

    "to days" {
        Date(2000, 2, 29).toDays() shouldBe 730545
    }

    "from days" {
        Date.fromDays(730545) shouldBe Date(2000, 2, 29)
        Date.fromDays(0) shouldBe null
    }

    "to posix seconds" {
        Date(2000, 2, 29).toPosixSeconds() shouldBe 951782400
        Date(1, 1, 1).toPosixSeconds() shouldBe null
        Date(2038, 1, 20).toPosixSeconds() shouldBe 2147558400
    }

    "from posix seconds" {
        Date.fromPosixSeconds(951782400) shouldBe Date(2000, 2, 29)
        Date.fromPosixSeconds(0) shouldBe Date(1970,1,1)
        Date.fromPosixSeconds(-1) shouldBe null
        Date.fromPosixSeconds(Long.MAX_VALUE) shouldBe null
    }

})