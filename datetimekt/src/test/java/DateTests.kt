import io.kotlintest.data.forall
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.withClue
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
            val date = Date(inputD, inputM, inputY)
            date.getDate() shouldBe expectedD
            date.getMonth() shouldBe expectedM
            date.getYear() shouldBe expectedY
        }
    }

    "date to string" {
        forall(
                row("2000-05-01", "1 May 2000", 1, 5, 2000),
                row("0050-01-01", "1 Jan 0050", 1, 1, 50),
                row("9999-01-01", "1 Jan 9999", 1, 13, 10000)
        ) { string, readableString, d, m, y ->
            Date(d, m, y).toString() shouldBe string
            Date(d, m, y).toReadableString() shouldBe readableString
        }
    }

    "equality" {
        Date(1, 5, 9) shouldBe Date(1, 5, 9)
    }

    "comparison" {
            val dates = arrayOf(
                    Date(5, 6, 2000),
                    Date(5, 6, 2000),
                    Date(4, 7, 2000),
                    Date(1, 1, 2001)
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
                row(Date(10, 6, 2000), "2000-06-10"),
                row(Date(10, 4, 2000), "2000-16-10"),
                row(null, "20000610"),
                row(null, "20O0-06-10")
        ) { expected, string ->
            Date.fromString(string) shouldBe expected
        }
    }

    "to month" {
        Date(5, 6, 7).toMonth() shouldBe Month(7, 6)
    }

    "to days" {
        Date(29, 2, 2000).toDays() shouldBe 730545
    }

    "from days" {
        Date.fromDays(730545) shouldBe Date(29, 2, 2000)
        Date.fromDays(0) shouldBe null
    }

})