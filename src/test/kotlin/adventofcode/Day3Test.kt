package adventofcode

import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class Day3Test : StringSpec({
    val puzzle = Day3()
    "wires" {
        wire("U2,R1") shouldBe listOf(Pair(0, 1), Pair(0, 2), Pair(1, 2))
        wire("U7,R6,D4,L4") shouldHaveSize (21)
    }
    "closest intersection" {
        val wire1 = wire("R75,D30,R83,U83,L12,D49,R71,U7,L72")
        val wire2 = wire("U62,R66,U55,R34,D71,R55,D58,R83")
        val point = closestIntersection(wire1, wire2)
        manhattan(point!!, ORIGIN) shouldBe 159
    }
    "first intersection" {
        val wire1 = wire("R75,D30,R83,U83,L12,D49,R71,U7,L72")
        val wire2 = wire("U62,R66,U55,R34,D71,R55,D58,R83")
        firstIntersection(wire1, wire2) shouldBe 610
    }

    "answers" {
        println(puzzle.answer1())
        println(puzzle.answer2())
    }
})

