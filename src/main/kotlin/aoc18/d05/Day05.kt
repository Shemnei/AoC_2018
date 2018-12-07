package aoc18.d05

import aoc18.util.AoC
import aoc18.util.Day
import aoc18.util.MyAoCSettings
import java.util.*

object Day05 : Day<CharArray>() {

    override fun prepare(input: String) = input.toCharArray()

    // < 69144
    override fun taskOne(input: CharArray): String {
        return react(input.asList()).size.toString()
    }

    override fun taskTwo(input: CharArray): String {
        val types = input.map { it.toLowerCase() }.distinct()
        return types.asSequence().map { type ->
            react(input.toMutableList().filter { it != type && it != type.toUpperCase() }).size
        }.min().toString()
    }

    private tailrec fun react(input: List<Char>): List<Char> {
        val size = input.size
        val tmp = Stack<Char>()
        input.forEach {
            if (!tmp.isEmpty() && tmp.peek() != it && tmp.peek().toUpperCase() == it.toUpperCase()) {
                tmp.pop()
            } else {
                tmp.push(it)
            }
        }

        return if (tmp.size == size) {
            tmp
        } else {
            react(tmp)
        }
    }
}

fun main(args: Array<String>) {
    AoC.settings = MyAoCSettings
    Day05.runTesting()
}