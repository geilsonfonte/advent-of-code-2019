package adventofcode

import adventofcode.ParameterMode.*
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class Day5Test : StringSpec({
    val puzzle = Day5()

    "instructions" {
        instruction(1002) shouldBe Pair(Opcode.MULTIPLY, listOf(POSITION, IMMEDIATE, POSITION))
        instruction(2) shouldBe Pair(Opcode.MULTIPLY, listOf(POSITION, POSITION, POSITION))
        instruction(3) shouldBe Pair(Opcode.INPUT, listOf(POSITION))
        instruction(99) shouldBe Pair(Opcode.HALT, listOf<ParameterMode>())
    }

    "intcode" {
        Intcode(listOf(1002,4,3,4,33)).finalState() shouldBe listOf(1002,4,3,4,99)
    }
    "answers" {
        println(puzzle.answer1())
        println(puzzle.answer2())
    }
})

