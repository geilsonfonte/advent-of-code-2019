package adventofcode

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class Day2Test : StringSpec({
    val puzzle = Day2()
    "intcode" {
        Intcode(listOf(1,0,0,0,99)).finalState() shouldBe listOf(2,0,0,0,99)
        Intcode(listOf(2,4,4,5,99,0)).finalState() shouldBe listOf(2,4,4,5,99,9801)
        Intcode(listOf(1,1,1,4,99,5,6,0,99)).finalState() shouldBe listOf(30,1,1,4,2,5,6,0,99)
    }
    "answers" {
        println(puzzle.answer1())
        println(puzzle.answer2())
    }
})
