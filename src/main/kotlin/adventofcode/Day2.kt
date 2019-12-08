package adventofcode

import java.lang.IllegalStateException

class Day2 : Puzzle {
    override val day = 2
    private val input = input().split(",").map(String::toInt)
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

data class ComputerState(val memory: List<Int>, val pointer: Int, val registerValue: Int)

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
            val (opcode, modes) = instruction(code[i])
            opcode.execute(code, i, modes)
            if (opcode == Opcode.HALT) {
                return
            }
            i += opcode.args + 1
        }
    }

    fun finalState(): List<Int> {
        return code
    }

    fun output() = finalState()[0]
}

enum class ParameterMode(val value: Int) {
    POSITION(0) {
        override fun read(memory: List<Int>, idx: Int): Int {
            return memory[memory[idx]]
        }
    },
    IMMEDIATE(1) {
        override fun read(memory: List<Int>, idx: Int): Int {
            return memory[idx]
        }
    };

    abstract fun read(memory: List<Int>, idx: Int): Int

    companion object {
        private val map = ParameterMode.values().associateBy(ParameterMode::value)
        operator fun get(value: Int) = map[value] ?: error("invalid argument: $value")
        operator fun get(value: Char) = get(value.toString().toInt())
    }
}

enum class Opcode(val code: Int, val args: Int) {
    ADD(1, 3) {
        override fun execute(memory: MutableList<Int>, pointer: Int, modes: List<ParameterMode>) {
            val operand1 = modes[0].read(memory, pointer + 1)
            val operand2 = modes[1].read(memory, pointer + 2)
            memory[memory[pointer + 3]] = operand1 + operand2
        }
    },
    MULTIPLY(2, 3) {
        override fun execute(memory: MutableList<Int>, pointer: Int, modes: List<ParameterMode>) {
            val operand1 = modes[0].read(memory, pointer + 1)
            val operand2 = modes[1].read(memory, pointer + 2)
            memory[memory[pointer + 3]] = operand1 * operand2
        }
    },
    HALT(99, 0) {
        override fun execute(memory: MutableList<Int>, pointer: Int, modes: List<ParameterMode>) {
            return
        }
    },
    INPUT(3, 1) {
        override fun execute(memory: MutableList<Int>, pointer: Int, modes: List<ParameterMode>) {
            memory[memory[pointer + 1]] = TODO()
        }
    },
    OUTPUT(4, 1) {
        override fun execute(memory: MutableList<Int>, pointer: Int, modes: List<ParameterMode>): Int {
            return modes[0].read(memory, pointer + 1)
        }
    },
    ;
//    abstract fun execute(state: ComputerState): ComputerState
    fun execute(memory: MutableList<Int>, pointer: Int): Any = execute(memory, pointer, List(this.args) {ParameterMode.POSITION})
    abstract fun execute(memory: MutableList<Int>, pointer: Int, modes: List<ParameterMode>): Any
    companion object {
        private val map = Opcode.values().associateBy(Opcode::code)
        operator fun get(value: Int) = map[value] ?: error("invalid argument: $value")
    }
}

fun instruction(int: Int): Pair<Opcode, List<ParameterMode>> {
    val opcode = Opcode[int % 100]
    val parameterRange = 2 until 2 + opcode.args
    val modes = parameterRange.map { idx -> ParameterMode[int.toString().reversed().getOrElse(idx) { '0' }] }

    return Pair(opcode, modes)
}