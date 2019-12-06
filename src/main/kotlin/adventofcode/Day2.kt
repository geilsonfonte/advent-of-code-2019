package adventofcode

import java.lang.IllegalStateException

class Day2 : Puzzle {
    override val day = 2
    private val input = input().trim().split(",").map(String::toInt)
    private val desiredOutput = 19690720

    override fun answer1(): Int {
        return Intcode(input, 12, 2).output()
    }

    override fun answer2(): Int {
        for (noun in 0..99) {
            for (verb in 0..99) {
                if (Intcode(input, noun, verb).output() == desiredOutput) {
                    return 100 * noun + verb
                }
            }
        }
        throw IllegalStateException()
    }
}

class Intcode {
    private val code: MutableList<Int>

    constructor(code: List<Int>, noun: Int, verb: Int) {
        this.code = code.toMutableList()
        this.code[1] = noun
        this.code[2] = verb
        run()
    }

    constructor(code: List<Int>) {
        this.code = code.toMutableList()
        run()
    }
    private fun run() {
        var i = 0
        while (true) {
            when (code[i]) {
                1 -> {
                    val operand1 = code[code[i + 1]]
                    val operand2 = code[code[i + 2]]
                    code[code[i + 3]] = operand1 + operand2
                }
                2 -> {
                    val operand1 = code[code[i + 1]]
                    val operand2 = code[code[i + 2]]
                    code[code[i + 3]] = operand1 * operand2
                }
                99 -> return
            }
            i += 4
        }
    }

    fun finalState(): List<Int> {
        return code
    }

    fun output() = finalState()[0]
}