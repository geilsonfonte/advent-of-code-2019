package adventofcode

import java.lang.IllegalStateException

class Day2 : Puzzle {
    override val day = 2
    private val input = input().split(",").map(String::toInt)
    private val desiredOutput = 19690720

    override fun answer1(): Int {
        return Intcode(initializeInput(12, 2)).finalState()[0]
    }

    override fun answer2(): Int {
        for (noun in 0..99) {
            for (verb in 0..99) {
                val modifiedInput = initializeInput(noun, verb)
                if (Intcode(modifiedInput).finalState()[0] == desiredOutput) {
                    return 100 * noun + verb
                }
            }
        }
        throw IllegalStateException()
    }

    private fun initializeInput(noun: Int, verb: Int): MutableList<Int> {
        val modifiedInput = input.toMutableList()
        modifiedInput[1] = noun
        modifiedInput[2] = verb
        return modifiedInput
    }
}

data class ComputerState(val memory: MutableList<Int>, var pointer: Int = 0, var registerValue: Int = 0) {
    fun nextInstruction(): Int {
        return memory[pointer]
    }
}

class Intcode {
    private val state: ComputerState

    constructor(code: List<Int>) : this(ComputerState(code.toMutableList())) { }

    constructor(state: ComputerState) {
        this.state = state
        run()
    }

    private fun run() {
        while (true) {
            val (opcode, modes) = instruction(state.nextInstruction())
            if (opcode == Opcode.HALT) {
                return
            }
            opcode.execute(state, modes)
        }
    }

    fun finalState(): List<Int> {
        return state.memory
    }

    fun output() = state.registerValue
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

enum class Opcode(val code: Int, val args: Int = 0) {
    ADD(1, 3) {
        override fun execute(state: ComputerState, modes: List<ParameterMode>) {
            val operand1 = modes[0].read(state.memory, state.pointer + 1)
            val operand2 = modes[1].read(state.memory, state.pointer + 2)
            state.memory[state.memory[state.pointer + 3]] = operand1 + operand2
            state.pointer += 4
        }
    },
    MULTIPLY(2, 3) {
        override fun execute(state: ComputerState, modes: List<ParameterMode>) {
            val operand1 = modes[0].read(state.memory, state.pointer + 1)
            val operand2 = modes[1].read(state.memory, state.pointer + 2)
            state.memory[state.memory[state.pointer + 3]] = operand1 * operand2
            state.pointer += 4
        }
    },
    HALT(99) {
        override fun execute(state: ComputerState, modes: List<ParameterMode>) {
        }
    },
    INPUT(3, 1) {
        override fun execute(state: ComputerState, modes: List<ParameterMode>) {
            val memory = state.memory
            memory[memory[state.pointer + 1]] = state.registerValue
            state.pointer += 2
        }
    },
    OUTPUT(4, 1) {
        override fun execute(state: ComputerState, modes: List<ParameterMode>) {
            val registerValue = modes[0].read(state.memory, state.pointer + 1)
            state.registerValue = registerValue
            state.pointer += 2
        }
    },
    JUMP_IF_TRUE(5, 2) {
        override fun execute(state: ComputerState, modes: List<ParameterMode>) {
            val test = modes[0].read(state.memory, state.pointer + 1)
            val newPointer = modes[1].read(state.memory, state.pointer + 2)
            if (test != 0) {
                state.pointer = newPointer
            } else {
                state.pointer += 3
            }
        }
    },
    JUMP_IF_FALSE(6, 2) {
        override fun execute(state: ComputerState, modes: List<ParameterMode>) {
            val test = modes[0].read(state.memory, state.pointer + 1)
            val newPointer = modes[1].read(state.memory, state.pointer + 2)
            if (test == 0) {
                state.pointer = newPointer
            } else {
                state.pointer += 3
            }
        }
    },
    LESS_THAN(7, 3) {
        override fun execute(state: ComputerState, modes: List<ParameterMode>) {
            val a = modes[0].read(state.memory, state.pointer + 1)
            val b = modes[1].read(state.memory, state.pointer + 2)
            state.memory[state.memory[state.pointer + 3]] = if (a < b) 1 else 0
            state.pointer += 4
        }
    },
    EQUALS(8, 3) {
        override fun execute(state: ComputerState, modes: List<ParameterMode>) {
            val a = modes[0].read(state.memory, state.pointer + 1)
            val b = modes[1].read(state.memory, state.pointer + 2)
            state.memory[state.memory[state.pointer + 3]] = if (a == b) 1 else 0
            state.pointer += 4
        }
    },
    ;
    abstract fun execute(state: ComputerState, modes: List<ParameterMode>)
//    fun execute(memory: MutableList<Int>, pointer: Int): Any = execute(memory, pointer, List(this.args) {ParameterMode.POSITION})
//    abstract fun execute(memory: MutableList<Int>, pointer: Int, modes: List<ParameterMode>): Any
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