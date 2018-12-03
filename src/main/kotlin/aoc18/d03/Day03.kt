package aoc18.d03

import aoc18.util.AoC
import aoc18.util.Day
import aoc18.util.MyAoCSettings

object Day03 : Day<Map<Int, Rectangle>>() {

    override val resultTaskOne: String = "113966"
    override val resultTaskTwo: String = "235"

    override fun prepare(input: String): Map<Int, Rectangle> {
        return input.lines()
            .mapNotNull {
                Regex("""^#(\d+)\s*@\s*(\d+)\s*,\s*(\d+)\s*:\s*(\d+)\s*x\s*(\d+)$""").matchEntire(it)
            }
            .associate {
                it.groupValues[1].toInt() to Rectangle.of(
                    it.groupValues[2].toInt(),
                    it.groupValues[3].toInt(),
                    it.groupValues[4].toInt(),
                    it.groupValues[5].toInt()
                )
            }
    }

    override fun taskOne(input: Map<Int, Rectangle>): String {
        val claims = input.toMutableMap()
        val overlap = mutableSetOf<Pair<Int, Int>>()

        input
            .asSequence()
            .map{ clm ->
                claims.remove(clm.key)!!
                claims.values.mapNotNull {
                    it.intersection(clm.value)
                }
            }
            .flatten()
            .forEach { rec ->
                (rec.x1 until rec.x2).forEach { x ->
                    (rec.y1 until rec.y2).forEach { y ->
                        overlap.add(x to y)
                    }
                }
            }
        return overlap.size.toString()
    }

    override fun taskTwo(input: Map<Int, Rectangle>): String {
        return input
            .asSequence()
            .map { clm ->
                val claims = input.toMutableMap()
                claims.remove(clm.key)
                clm.key to claims.values.any {
                    it.intersection(clm.value) != null
                }
            }
            .first { !it.second }.first.toString()
    }
}

fun main(args: Array<String>) {
    AoC.settings = MyAoCSettings
    Day03.runTesting()
}