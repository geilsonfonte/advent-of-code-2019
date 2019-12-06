package adventofcode

import java.io.File

interface Puzzle {
    val day: Int
    fun input(): String {
        return File("src/main/resources/input/${day}.txt").readText().trim()
    }
    fun answer1(): Any
    fun answer2(): Any
}