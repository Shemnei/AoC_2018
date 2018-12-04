package aoc18.d04

import aoc18.util.AoC
import aoc18.util.Day
import aoc18.util.MyAoCSettings
import java.text.ParseException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Day04 : Day<Set<Guard>>() {

    override val resultTaskOne: String = "94040"
    override val resultTaskTwo: String = "39940"

    override fun prepare(input: String): Set<Guard> {
        val guards = mutableMapOf<Int, Guard>()
        var currentGuard: Guard? = null
        var lastMinute: Int = 0

        input.lines()
            .map {
                Regex("""^\[(\d{4}-\d{2}-\d{2} \d{2}:\d{2})] (.*)$""").find(it)!!
            }
            .map {
                LocalDateTime.parse(
                    it.groupValues[1],
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                ) to it.groupValues[2]
            }
            .sortedBy { it.first }
            .forEach {
                if (it.second.startsWith("Guard")) {
                    val id = Regex("""^Guard #(\d+) begins shift$""").find(it.second)!!.groupValues[1].toInt()
                    currentGuard = guards.computeIfAbsent(id) { Guard(id) }
                    lastMinute = 0
                } else {
                    val date = it.first.toLocalDate()
                    val minutes = it.first.minute
                    when {
                        it.second == "falls asleep" -> {
                            val shift = currentGuard!!.shifts.computeIfAbsent(date) { Shift(date) }
                            shift.wakeHours.add(lastMinute until minutes)
                        }
                        it.second == "wakes up" -> {
                            val shift = currentGuard!!.shifts.computeIfAbsent(date) { Shift(date) }
                            shift.sleepHours.add(lastMinute until minutes)
                        }
                        else -> throw ParseException(it.second, 0)
                    }
                    lastMinute = minutes
                }
            }
        return guards.values.toSet()
    }


    override fun taskOne(input: Set<Guard>): String {
        val guard = input.maxBy { it.totalSleepTime }!!
        return (guard.minuteMostSlept!!.first * guard.id).toString()
    }

    override fun taskTwo(input: Set<Guard>): String {
        val guard = input.filter { it.minuteMostSlept != null }.maxBy { it.minuteMostSlept!!.second }!!
        return (guard.id * guard.minuteMostSlept!!.first).toString()
    }
}

fun main(args: Array<String>) {
    AoC.settings = MyAoCSettings
    Day04.runTesting()
}