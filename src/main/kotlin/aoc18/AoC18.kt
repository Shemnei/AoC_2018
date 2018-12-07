package aoc18

import aoc18.d01.Day01
import aoc18.d02.Day02
import aoc18.d03.Day03
import aoc18.d04.Day04
import aoc18.d05.Day05
import aoc18.d07.Day07
import aoc18.util.AoC
import aoc18.util.MyAoCSettings
import aoc18.util.SimpleResultFormat
import klog.Logger
import klog.Loggers
import klog.format.ColoredFormatDecorator
import klog.sink.ConsoleSink

private val resultLogger: Logger = Loggers.get("AoC18").apply {
    this.logSinks.add(ConsoleSink().apply {
        this.defaultFormat = ColoredFormatDecorator(SimpleResultFormat())
    })
}

val DAYS = arrayOf(
    Day01,
    Day02,
    Day03,
    Day04,
    Day05,
//    Day06,
    Day07
)

fun main(args: Array<String>) {
    AoC.settings = MyAoCSettings

    DAYS.forEach {
        resultLogger.verbose("DAY ${it.day}")
        it.runTesting()
        println()
    }
}