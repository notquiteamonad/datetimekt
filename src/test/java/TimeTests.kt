import com.github.samueldple.datetimekt.Duration
import com.github.samueldple.datetimekt.Time
import io.kotlintest.data.forall
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class TimeTests: StringSpec ({

    "times" {
        forall(
                row(5, 30, 0, Time(5, 30, 0)),
                row(5, 30, 30, Time(5, 30, 30)),
                row(6, 31, 30, Time(6, 30, 90)),
                row(7, 30, 30, Time(6, 90, 30)),
                row(1, 30, 30, Time(25, 30, 30)),
                row(2, 31, 30, Time(25, 90, 90)),
                row(1, 0, 0, Time(18, 420, 0)),
                row(6, 29, 0, Time(6, 30, -60)),
                row(18, 58, 59, Time(-3, -116, -301))
        ) { h, m, s, time ->
            time.getHours() shouldBe h
            time.getMinutes() shouldBe m
            time.getSeconds() shouldBe s
        }
    }

    "hashcode" {
        Time(1, 2, 3).hashCode() shouldBe 1026
    }

    "times to strings" {
        val time = Time(3, 0, 39)
        time.toString() shouldBe "03:00:39"
        time.toHHMMString() shouldBe "03:00"
    }

    "times from strings" {
        val time = Time(3, 0, 39)
        Time.fromString("03:00:39") shouldBe time
        Time.fromString("05:a:30") shouldBe null
        Time.fromString("05:5:30") shouldBe null
    }

    "times from seconds" {
        forall(
                row(Time(0, 0, 0), 86400),
                row(Time(23, 59, 59), -1)
        ) { time, seconds ->
            Time(seconds) shouldBe time
        }
    }

    "times to seconds" {
        forall(
                row(0, Time(0, 0, 0)),
                row(86399, Time(23, 59, 59))
        ) { timeInSeconds, time ->
            time.toSeconds() shouldBe timeInSeconds
        }
    }

    "times to minutes" {
        forall(
                row(0, Time(0, 0, 0)),
                row(1439, Time(23, 59, 59))
        ) { timeInMinutes, time ->
            time.toMinutes() shouldBe timeInMinutes
        }
    }

    "time comparisons" {
        val time1 = Time(0, 0, 0)
        val time2 = Time(1, 1, 1)
        val time3 = Time(2, 2, 2)
        assert(time1 < time2)
        assert(time1 <= time2)
        assert(time2 <= time2)
        assert(time2 < time3)
        assert(time1 < time3)
        assert(time1 <= time3)
    }

    "time operations" {
        val time0 = Time(0, 0, 0)
        val time1 = Time(1, 1, 1)
        val time2 = Time(2, 2, 2)
        (time1 + time1) shouldBe time2
        (time1 - time1) shouldBe time0
        var time3 = time0
        time3 -= time1
        time3 shouldBe Time(22, 58, 59)
    }

    "time to duration" {
        Time(1, 2, 3).toDuration() shouldBe Duration(1, 2, 3)
    }

    "durations" {
        var duration = Duration(86401)
        duration.getHours() shouldBe 24
        duration.getMinutes() shouldBe 0
        duration.getSeconds() shouldBe 1
        duration += Time(1, 0, 0)
        duration.getHours() shouldBe 25
        duration.toTime() shouldBe Time(1, 0, 1)
    }

    "durations to strings" {
        forall(
                row("08:30:30", "08:30", 8, 30, 30),
                row("100:00:00", "100:00", 100, 0, 0)
        ) { string, hhmmString, h, m, s ->
            val duration = Duration(h, m, s)
            duration.toString() shouldBe string
            duration.toHHMMString() shouldBe hhmmString
        }
    }

    "durations from strings" {
        forall(
                row(8, 30, 30, "08:30:30"),
                row(100, 0, 0, "100:00:00"),
                row(1, 0, 0, "1:00:00")
        ) { h, m, s, string ->
            val duration = Duration.fromString(string)!!
            duration.getHours() shouldBe h
            duration.getMinutes() shouldBe m
            duration.getSeconds() shouldBe s
        }
    }

    "no negatives" {
        assertAll { h: Int, m: Int, s: Int ->
            Time(h, m, s).toSeconds() >= 0
            Duration(h, m, s).toSeconds() >= 0
        }
        assertAll { s: Int ->
            Time(s).toSeconds() >= 0
            Duration(s).toSeconds() >= 0
        }
    }

})