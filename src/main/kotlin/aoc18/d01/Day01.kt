package aoc18.d01

import aoc18.util.*

object Day01 : Day<List<Int>>() {

    override fun prepare(input: String): List<Int> = input.lines().map { it.toInt() }

    override fun taskOne(input: List<Int>) = input.sum().toString()

    override fun taskTwo(input: List<Int>): String {
        val frequencies = mutableSetOf(0)
        return input.cycle().accumulate().first {
            !frequencies.add(it)
        }.toString()
    }
}

fun main(args: Array<String>) {
    AoC.settings = MyAoCSettings
    Day01.runTesting()
}