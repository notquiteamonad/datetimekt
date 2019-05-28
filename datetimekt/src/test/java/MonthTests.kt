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
            val month = Month(inputM, inputY)
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
            Month(m, y).toString() shouldBe string
            Month(m, y).toReadableString() shouldBe readableString
        }
    }

    "all values valid" {
        assertAll { m: Int, y: Int ->
            val month = Month(m, y)
            month.getYear() in 0..9999
            month.getMonth() in 1..12
        }
    }

    "equality" {
        Month(5, 2000) shouldBe Month(5, 2000)
    }

    "comparison" {
        val months = arrayOf(Month(5, 2000), Month(5, 2000), Month(6, 2000), Month(1, 2001))
        withClue("comparison1") {(months[0] <= months[1]).shouldBeTrue()}
        withClue("comparison2") {(months[0] < months[1]).shouldBeFalse()}
        withClue("comparison3") {(months[0] >= months[1]).shouldBeTrue()}
        withClue("comparison4") {(months[0] < months[2]).shouldBeTrue()}
        withClue("comparison5") {(months[2] < months[3]).shouldBeTrue()}
        withClue("comparison6") {(months[3] > months[1]).shouldBeTrue()}
    }

})