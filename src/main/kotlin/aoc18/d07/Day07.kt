package aoc18.d07

import aoc18.util.AoC
import aoc18.util.Day
import aoc18.util.MyAoCSettings

object Day07 : Day<Map<Char, MutableSet<Char>>>() {

    override fun prepare(input: String): Map<Char, MutableSet<Char>> {
        val regex = Regex("""^Step ([A-Z]) must be finished before step ([A-Z]) can begin.$""")
        val dependencies =
            input.lines().map { regex.find(it)!! }.map { setOf(it.groupValues[1][0], it.groupValues[2][0]) }
        val steps = dependencies.flatten().toSet()
        val dependencyMap = steps.associate { it to mutableSetOf<Char>() }.toMutableMap()
        dependencies.forEach {
            dependencyMap.compute(it.last()) { _, v ->
                (v ?: mutableSetOf()).apply { add(it.first()) }
            }
        }
        return dependencyMap
    }

    override fun taskOne(input: Map<Char, MutableSet<Char>>): String {
        val wQueue = WorkerQueue(1, prepare(this.input))
        return wQueue.run().second
    }

    override fun taskTwo(input: Map<Char, MutableSet<Char>>): String {
        val wQueue = WorkerQueue(5, prepare(this.input))
        return wQueue.run().first.toString()
    }

}

fun main(args: Array<String>) {
    AoC.settings = MyAoCSettings
    Day07.runTesting()
}