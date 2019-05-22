import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class TimeTests: StringSpec ({

    "times" {
        forall(
                row(5, 30, 0, Time.new(5, 30, 0)),
                row(5, 30, 30, Time.new(5, 30, 30)),
                row(6, 31, 30, Time.new(6, 30, 90)),
                row(7, 30, 30, Time.new(6, 90, 30)),
                row(1, 30, 30, Time.new(25, 30, 30)),
                row(2, 31, 30, Time.new(25, 90, 90)),
                row(1, 0, 0, Time.new(18, 420, 0)),
                row(6, 29, 0, Time.new(6, 30, -60)),
                row(18, 58, 59, Time.new(-3, -116, -301))
        ) { h, m, s, time ->
            time.getHours() shouldBe h
            time.getMinutes() shouldBe m
            time.getSeconds() shouldBe s
        }
    }

    "times to strings" {
        val time = Time.new(3, 0, 39)
        time.toString() shouldBe "03:00:39"
        time.toHHMMString() shouldBe "03:00"
    }

    "times from seconds" {
        forall(
                row(Time.new(0, 0, 0), Time.fromSeconds(86400)),
                row(Time.new(23, 59, 59), Time.fromSeconds(-1))
        ) { time, timeFromSeconds ->
            timeFromSeconds shouldBe time
        }
    }

    "times to seconds" {
        forall(
                row(0, Time.new(0, 0, 0)),
                row(86399, Time.new(23, 59, 59))
        ) { timeInSeconds, time ->
            time.toSeconds() shouldBe timeInSeconds
        }
    }

    "times to minutes" {
        forall(
                row(0, Time.new(0, 0, 0)),
                row(1439, Time.new(23, 59, 59))
        ) { timeInMinutes, time ->
            time.toMinutes() shouldBe timeInMinutes
        }
    }

    //todo + -
    //todo < > <= >=
    //todo fromString
    //todo add/subtractSeconds
    //todo add/subtractMinutes
    //todo add/subtractHours
    //todo toDuration
    //todo duration: larger than 1 day

})