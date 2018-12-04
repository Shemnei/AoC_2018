package aoc18.d04

import java.time.LocalDate

data class Shift(
    val date: LocalDate,
    val wakeHours: MutableSet<IntRange> = mutableSetOf(),
    val sleepHours: MutableSet<IntRange> = mutableSetOf()
) {

    val sleepTime: Int
        get() = sleepHours.sumBy { it.count() }
}