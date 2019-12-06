package adventofcode

import io.kotlintest.matchers.startWith
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.io.File

class Day1Test : StringSpec({
    val day1 = Day1()
    "fuel" {
        day1.fuel(12) shouldBe 2
        day1.fuel(14) shouldBe 2
        day1.fuel(100756) shouldBe 33583
        day1.fuel(2) shouldBe 0
    }
    "compound fuel" {
        day1.compoundFuel(12) shouldBe 2
        day1.compoundFuel(100756) shouldBe 50346
    }
    "answers" {
        println(day1.answer1())
        println(day1.answer2())
    }
})
