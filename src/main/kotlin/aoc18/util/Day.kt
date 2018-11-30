package aoc18.util

import klog.Logger
import klog.Loggers
import klog.format.ColoredFormatDecorator
import klog.sink.ConsoleSink

abstract class Day(val year: Int, day: Int = -1) {

    val logger: Logger = Loggers.get(this.javaClass.simpleName).apply {
        this.logSinks.add(ConsoleSink().apply {
            this.defaultFormat = ColoredFormatDecorator(SimpleTimeFormat())
        })
    }

    val day: Int = if (day > 0) {
        day
    } else {
        Regex("""\d{1,2}""").find(this.javaClass.simpleName)?.value?.toInt()
            ?: Regex("""\d{1,2}""").find(this.javaClass.packageName.split(".").asReversed().joinToString("."))?.value?.toInt()
            ?: throw IllegalArgumentException("No day given and could not parse day from class name or package")
    }

    val input: String by lazy { AoC.getInput(this.year, this.day) }

    init {
        AoC.validateDate(this.year, this.day)
    }

    abstract fun runOne(input: String)
    abstract fun runTwo(input: String)

    fun runDefaultOne() = runOne(input)
    fun runDefaultTwo() = runTwo(input)

    fun run() {
        runDefaultOne()
        runDefaultTwo()
    }

    fun d(msg: String) = logger.debug(msg)
    fun d(lazyMsg: () -> String) = logger.debug(lazyMsg())
    fun i(msg: String) = logger.info(msg)
    fun i(lazyMsg: () -> String) = logger.info(lazyMsg())
}