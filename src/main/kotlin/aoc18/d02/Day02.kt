package aoc18.d02

import aoc18.util.AoC
import aoc18.util.Day
import aoc18.util.MyAoCSettings

object Day02 : Day<List<String>>() {

    override fun prepare(input: String) = input.lines()

    override fun taskOne(input: List<String>): String {
        var twos = 0
        var threes = 0
        input.forEach { id ->
            val grps = id.groupBy { it }.map { it.value.size }
            twos += if (2 in grps) 1 else 0
            threes += if (3 in grps) 1 else 0
        }
        return (twos * threes).toString()
    }

    override fun taskTwo(input: List<String>): String {
        input.forEach { id ->
            val charsId = id.toCharArray()
            val r = input.firstOrNull { box ->
                box.toCharArray()
                    .zip(charsId)
                    .filter {
                        it.first != it.second
                    }.size == 1
            }
            if (r != null) return r.filter { it in id }
        }
        return "Error"
    }
}

fun main(args: Array<String>) {
    AoC.settings = MyAoCSettings
    Day02.runTesting()
}