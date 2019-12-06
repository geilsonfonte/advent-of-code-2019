package adventofcode

const val STRICT = true

class Day4 : Puzzle {
    override val day = 4
    private val input = input().split('-').map(String::toInt)
    private val range: IntRange


    init {
        this.range = input.first().rangeTo(input.last())
    }
    override fun answer1(): Int {
        return range.filter { valid(it) }.size
    }

    override fun answer2(): Int {
        return range.filter { valid(it, STRICT) }.size
    }

    fun valid(int: Int, strict: Boolean = false): Boolean {
        val digits = int.toString().toList()
        val isSorted = digits == (digits.sorted())
        val hasDuplicate = digits.toSet().size < digits.size
        if (strict) {
            val hasDuplicateOfExactTwo = digits.any { d -> digits.count { it == d } == 2 }
            return isSorted && hasDuplicateOfExactTwo
        } else {
            return isSorted && hasDuplicate
        }
    }


}



