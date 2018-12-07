package aoc18.d07

import aoc18.util.AoC
import aoc18.util.Day
import aoc18.util.MyAoCSettings

object Day07 : Day<Map<Char, MutableList<Char>>>() {

    override val resultTaskOne: String = "CFGHAEMNBPRDISVWQUZJYTKLOX"
    override val resultTaskTwo: String = "828"

    override fun prepare(input: String): Map<Char, MutableList<Char>> {
        val regex = Regex("""^Step ([A-Z]) must be finished before step ([A-Z]) can begin.$""")
        val dependencies =
            input.lines().map { regex.find(it)!! }.map { listOf(it.groupValues[1][0], it.groupValues[2][0]) }
        val steps = dependencies.flatten().toSet()
        val dependencyMap = steps.associate { it to mutableListOf<Char>() }.toMutableMap()
        dependencies.forEach { dependencyMap.compute(it[1]) { _, v -> (v ?: mutableListOf()).apply { add(it[0]) } } }
        return dependencyMap
    }

    override fun taskOne(input: Map<Char, MutableList<Char>>): String {
        val cpy = input.toMutableMap()
        var out = ""
        while (cpy.isNotEmpty()) {
            val char = cpy.filter { it.value.isEmpty() }.minBy { it.key }!!.key
            out += char
            cpy.remove(char)
            cpy.mapValues { it.value.apply { remove(char) } }
        }
        return out
    }

    override fun taskTwo(input: Map<Char, MutableList<Char>>): String {
        val wQueue = WorkerQueue(5, prepare(this.input))
        return wQueue.run().toString()
    }

}

fun main(args: Array<String>) {
    AoC.settings = MyAoCSettings
    Day07.runTesting()
}