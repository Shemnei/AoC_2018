package aoc18.util

open class AoCException(msg: String) : Exception(msg)

open class NoAnswerUnlockedException(msg: String) : AoCException(msg) {
    constructor(year: Int, day: Int) : this("No unlocked Answers for $year-12-${day.toString().padStart(2, '0')}")
}

open class OnylOneAnswerUnlockedException(msg: String) : AoCException(msg) {
    constructor(year: Int, day: Int)
            : this("Only answer for task one unlocked for $year-${AoC.MONTH_OF_YEAR.value}-$day")
}