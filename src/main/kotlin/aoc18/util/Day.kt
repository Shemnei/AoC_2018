package aoc18.util

import klog.Logger
import klog.Loggers
import klog.format.ColoredFormatDecorator
import klog.sink.ConsoleSink
import java.time.Year
import kotlin.system.measureTimeMillis

abstract class Day<T>(year: Int = -1, day: Int = -1) {

    val year: Int = if (year > 2014) {
        year
    } else {
        val yearPart = Regex("""\d{2}|\d{4}""").find(this.javaClass.`package`.name.split(".").first())?.value
            ?: throw IllegalArgumentException("No year given and could not parse year from outermost package")
        if (yearPart.length == 4) yearPart.toInt()
        else (Year.now().toString().substring(0 until 2) + yearPart).toInt()
    }

    val day: Int = if (day > 0) {
        day
    } else {
        Regex("""\d{1,2}""").find(this.javaClass.simpleName)?.value?.toInt()
            ?: Regex("""\d{1,2}""").find(this.javaClass.`package`.name.split(".").asReversed().joinToString("."))?.value?.toInt()
            ?: throw IllegalArgumentException("No day given and could not parse day from class name or package")
    }

    val logger: Logger = Loggers.get(this.javaClass.simpleName).apply {
        this.logSinks.add(ConsoleSink().apply {
            this.defaultFormat = ColoredFormatDecorator(SimpleTimeFormat())
        })
    }

    private val resultLogger: Logger = Loggers.get("Day ${this.day.toString().padStart(2, '0')}").apply {
        this.logSinks.add(ConsoleSink().apply {
            this.defaultFormat = ColoredFormatDecorator(SimpleResultFormat())
        })
    }

    val input: String by lazy { AoC.getInput(this.year, this.day) }
    val preparedInput: T by lazy { prepare(this.input) }

    open val resultTaskOne: String = ""
    open val resultTaskTwo: String = ""

    init {
        AoC.validateDate(this.year, this.day)
    }

    abstract fun prepare(input: String): T

    open fun taskOne(input: T): String = ""
    open fun taskTwo(input: T): String = ""

    fun logOne(input: T = preparedInput): String {
        var result = ""
        val timeMs = measureTimeMillis { result = taskOne(input) }
        resultLogger.info("Task 1 => $result [${timeMs}ms]")
        return result
    }

    fun logOne(input: String) = logOne(prepare(input))

    fun logTwo(input: T = preparedInput): String {
        var result = ""
        val timeMs = measureTimeMillis { result = taskTwo(input) }
        resultLogger.info("Task 2 => $result [${timeMs}ms]")
        return result
    }

    fun logTwo(input: String) = logTwo(prepare(input))

    fun runLogging(input: T = preparedInput) {
        logOne(input)
        logTwo(input)
    }

    fun runLogging(input: String) {
        val prepared = prepare(input)
        logOne(prepared)
        logTwo(prepared)
    }

    fun testOne(input: T = preparedInput, expected: String = resultTaskOne, logTask: Boolean = true) {
        resultLogger.verbose("---------- Starting Test of Task One ----------")
        val isValue = if (logTask) logOne(input) else taskOne(input)
        if (isValue != expected) {
            resultLogger.error("Task One => Failed Test [Expected: $expected but is $isValue]")
        } else {
            resultLogger.info("Task One => Passed Test [Is: $expected]")
        }
    }

    fun testOne(input: String, expected: String, logTask: Boolean = true) = testOne(prepare(input), expected, logTask)

    fun testTwo(input: T = preparedInput, expected: String = resultTaskTwo, logTask: Boolean = true) {
        resultLogger.verbose("---------- Starting Test of Task Two ----------")
        val isValue = if (logTask) logTwo(input) else taskTwo(input)
        if (isValue != expected) {
            resultLogger.error("Task Two => Failed Test [Expected: $expected but is $isValue]")
        } else {
            resultLogger.info("Task Two => Passed Test [Is: $expected]")
        }
    }

    fun testTwo(input: String, expected: String, logTask: Boolean = true) = testTwo(prepare(input), expected, logTask)

    fun runTesting(
        input: T = preparedInput,
        expectedOne: String = resultTaskOne,
        expectedTwo: String = resultTaskTwo,
        logTask: Boolean = true
    ) {
        testOne(input, expectedOne, logTask)
        testTwo(input, expectedTwo, logTask)
    }

    fun runLogging(input: String, expectedOne: String, expectedTwo: String, logTask: Boolean = true) {
        val prepared = prepare(input)
        testOne(prepared, expectedOne, logTask)
        testTwo(prepared, expectedTwo, logTask)
    }

    fun d(msg: String) = logger.debug(msg)
    fun d(lazyMsg: () -> String) = logger.debug(lazyMsg())
    fun i(msg: String) = logger.info(msg)
    fun i(lazyMsg: () -> String) = logger.info(lazyMsg())
}