package adventofcode

import kotlin.math.abs

typealias Point = Pair<Int, Int>
typealias Wire = List<Point>

val DIRECTIONS = mapOf('U' to Pair(0, 1), 'R' to Pair(1, 0), 'D' to Pair(0, -1), 'L' to Pair(-1, 0))
val ORIGIN = Pair(0, 0)

fun manhattan(a: Point, b: Point = ORIGIN): Int {
    return abs(a.first - b.first) + abs(a.second - b.second)
}

fun closestIntersection(wire1: Wire, wire2: Wire, origin: Point = ORIGIN): Point? {
    return wire1.intersect(wire2).minBy { manhattan(it, origin) }
}

fun firstIntersection(wire1: Wire, wire2: Wire): Int? {
    return wire1.intersect(wire2).map { wire1.indexOf(it) + wire2.indexOf(it) + 2}.min()
}

fun wire(s: String): List<Point> {
    val instructions: List<Pair<Char, Int>> = s.split(',').map { Pair(it.first(), it.substring(1).toInt())}
    val path = mutableListOf<Point>()
    var position = Pair(0, 0)
    for ((dir, len) in instructions) {
        for (step in 1..len) {
            position = move(position, DIRECTIONS[dir] ?: error(""))
            path.add(position)
        }
    }
    return path
}

fun move(position: Pair<Int, Int>, direction: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(position.first + direction.first, position.second + direction.second)
}


class Day3 : Puzzle {
    override val day = 3
    private val input = input().lines()
    private val wire1: Wire
    private val wire2: Wire

    init {
        this.wire1 = wire(input.first())
        this.wire2 = wire(input.last())
    }

    override fun answer1(): Int {
        return manhattan(closestIntersection(wire1, wire2)!!)
    }

    override fun answer2(): Int {
        return firstIntersection(wire1, wire2)!!
    }
}
