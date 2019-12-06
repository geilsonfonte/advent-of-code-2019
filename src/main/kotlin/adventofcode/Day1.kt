package adventofcode

import kotlin.math.max

class Day1 : Puzzle {
    override val day = 1
    private val modules = input().trim().lines().map(String::toInt)

    fun fuel(mass: Int): Int {
        return max(0, mass / 3 - 2)
    }

    fun compoundFuel(mass: Int): Int {
        var remaining = fuel(mass)
        var total = 0
        while (remaining > 0) {
            total += remaining
            remaining = fuel(remaining)
        }
        return total
    }

    override fun answer1(): Int {
        return modules.map { fuel(it) }.sum()
    }

    override fun answer2(): Int {
        return modules.map { compoundFuel(it) }.sum()
    }

}



