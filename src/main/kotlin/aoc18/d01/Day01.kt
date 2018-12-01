package aoc18.d01

import aoc18.util.AoC
import aoc18.util.Day
import aoc18.util.MyAoCSettings
import aoc18.util.cycle

object Day01 : Day<List<Int>>() {

    override fun prepare(input: String): List<Int> = input.lines().map { it.toInt() }

    override fun taskOne(input: List<Int>) = input.sum().toString()

    override fun taskTwo(input: List<Int>): String {
        val seenFrequencies = mutableSetOf<Int>()
        var freq = 0
        return input.cycle().map {
            seenFrequencies += freq
            freq += it
            freq
        }.first { it in seenFrequencies }.toString()
    }
}

fun main(args: Array<String>) {
    AoC.settings = MyAoCSettings
    Day01.runLogging()
}