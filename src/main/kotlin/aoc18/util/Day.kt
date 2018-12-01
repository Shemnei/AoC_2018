package aoc18.util

import klog.Logger
import klog.Loggers
import klog.format.ColoredFormatDecorator
import klog.sink.ConsoleSink
import java.time.Year
import kotlin.system.measureTimeMillis

abstract class Day<T>(year: Int = - 1, day: Int = -1) {

    val logger: Logger = Loggers.get(this.javaClass.simpleName).apply {
        this.logSinks.add(ConsoleSink().apply {
            this.defaultFormat = ColoredFormatDecorator(SimpleTimeFormat())
        })
    }

    val year: Int = if (year > 2014) {
        year
    } else {
        val yearPart = Regex("""\d{2}|\d{4}""").find(this.javaClass.packageName.split(".").first())?.value
            ?: throw IllegalArgumentException("No year given and could not parse year from outermost package")
        if (yearPart.length == 4) yearPart.toInt()
        else (Year.now().toString().substring(0 until 2) + yearPart).toInt()
    }

    val day: Int = if (day > 0) {
        day
    } else {
        Regex("""\d{1,2}""").find(this.javaClass.simpleName)?.value?.toInt()
            ?: Regex("""\d{1,2}""").find(this.javaClass.packageName.split(".").asReversed().joinToString("."))?.value?.toInt()
            ?: throw IllegalArgumentException("No day given and could not parse day from class name or package")
    }

    val input: String by lazy { AoC.getInput(this.year, this.day) }
    val preparedInput: T by lazy { prepare(this.input) }

    init {
        AoC.validateDate(this.year, this.day)
    }

    abstract fun prepare(input: String): T

    open fun taskOne(input: T): String = ""
    open fun taskTwo(input: T): String = ""

    fun runDefaultOne() = taskOne(this.preparedInput)
    fun runDefaultTwo() = taskTwo(this.preparedInput)


    fun logOne(input: String) {
        logOne(prepare(input))
    }
    fun logOne(input: T) {
        var result = ""
        val timeMs = measureTimeMillis { result = taskOne(input) }
        println("Day ${this.day.toString().padStart(2, '0')} - Task 1 => $result [${timeMs}ms]")
    }
    fun logTwo(input: String) {
        logOne(prepare(input))
    }
    fun logTwo(input: T) {
        var result = ""
        val timeMs = measureTimeMillis { result = taskTwo(input) }
        println("Day ${this.day.toString().padStart(2, '0')} - Task 2 => $result [${timeMs}ms]")
    }

    fun logDefaultOne() = logOne(this.preparedInput)
    fun logDefaultTwo() = logTwo(this.preparedInput)

    fun runLogging() {
        logDefaultOne()
        logDefaultTwo()
    }

    fun d(msg: String) = logger.debug(msg)
    fun d(lazyMsg: () -> String) = logger.debug(lazyMsg())
    fun i(msg: String) = logger.info(msg)
    fun i(lazyMsg: () -> String) = logger.info(lazyMsg())
}