package aoc18.util

abstract class Day(val year: Int, day: Int = -1) {

    val day: Int = if (day > 0) {
        day
    } else {
        Regex("""\d{2}""").find(this.javaClass.simpleName)?.value?.toInt()
            ?: Regex("""\d{2}""").find(this.javaClass.packageName)?.value?.toInt()
            ?: throw IllegalArgumentException("No day given and could not parse day from class name or package")
    }

    val input: String by lazy { AoC.getInput(year, day) }

    init {
        AoC.validateDate(year, day)
    }

    abstract fun runOne(input: String)
    abstract fun runTwo(input: String)

    fun runDefaultOne() = runOne(input)
    fun runDefaultTwo() = runTwo(input)

    fun run() {
        runDefaultOne()
        runDefaultTwo()
    }
}