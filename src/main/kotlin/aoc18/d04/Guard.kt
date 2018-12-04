package aoc18.d04

import java.time.LocalDate

data class Guard(val id: Int, val shifts: MutableMap<LocalDate, Shift> = mutableMapOf()) {
    val totalSleepTime: Int
        get() = shifts.values.sumBy { it.sleepTime }

    val minuteMostSlept: Pair<Int, Int>?
        get() {
            val minutes = mutableMapOf<Int, Int>()
            shifts.values.forEach { shift ->
                shift.sleepHours.forEach { sleepMinutes ->
                    sleepMinutes.forEach {
                        minutes.compute(it) { _, v -> (v ?: 0) + 1 }
                    }
                }
            }
            return minutes.maxBy { it.value }?.toPair()
        }
}