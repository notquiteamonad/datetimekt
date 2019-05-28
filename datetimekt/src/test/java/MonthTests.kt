import io.kotlintest.data.forall
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
            month.getMonths() shouldBe expectedM
            month.getYears() shouldBe expectedY
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

})