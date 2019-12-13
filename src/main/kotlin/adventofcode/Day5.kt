package adventofcode

class Day5 : Puzzle {
    override val day = 5
    private val input = input().split(",").map(String::toInt)

    override fun answer1(): Int {
        val intcode = Intcode(ComputerState(input.toMutableList(), registerValue = 1))
        return intcode.output()
    }

    override fun answer2(): Int {
        return 0
    }
}



