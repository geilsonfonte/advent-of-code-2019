package adventofcode

import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class Day4Test : StringSpec({
    val puzzle = Day4()

    "answers" {
        println(puzzle.answer1())
        println(puzzle.answer2())
    }
})

