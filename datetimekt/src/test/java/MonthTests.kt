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

})