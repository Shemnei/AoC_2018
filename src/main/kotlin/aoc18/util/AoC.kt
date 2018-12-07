package aoc18.util

import klog.format.ColoredFormatDecorator
import klog.logger
import klog.sink.ConsoleSink
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths
import java.time.*
import javax.net.ssl.HttpsURLConnection

// TODO: 04.12.2018 logging
// TODO: 04.12.2018 check for answers every call to test ? maybe let user set bool to indicate unlocked solutions
// TODO: 04.12.2018 handle partial unlocked answers
object AoC {

    private val logger = logger(AoC::class.java.simpleName) {
        this.logSinks.add(ConsoleSink().apply {
            this.defaultFormat = ColoredFormatDecorator(SimpleTimeFormat())
        })
    }

    val MONTH_OF_YEAR = Month.DECEMBER
    var settings: AoCSettings = DefaultAoCSettings

    fun validateDate(year: Int, day: Int) {
        require(year > 2015) { "No time travel yet [is: $year, should: > 2015]" }
        require(year <= Year.now().value) { "No time travel yet [is: $year, should: <= ${Year.now()}]" }
        require(day in 1..25) { "Day must be in range of [1-25] is [$day]" }

        // TODO: 04.12.2018 test
        val nowEST = LocalDateTime.now().atZone(ZoneId.of("UTC-5")).toEpochSecond()
        val dateEST =
            LocalDateTime.of(year, MONTH_OF_YEAR.value, day, 0, 0).atOffset(ZoneOffset.ofHours(-5)).toEpochSecond()
        require(nowEST >= dateEST) { "Date lies in the FUTURE!" }
    }

    private fun formatTemplate(template: String, year: Int, day: Int): String {
        return template
            .replace("{{year}}", year.toString())
            .replace("{{day}}", day.toString())
            .replace("{{dday}}", day.toString().padStart(2, '0'))
    }

    fun getInput(
        year: Int,
        day: Int,
        forceRefresh: Boolean = false,
        sessionToken: String = settings.SESSION_TOKEN
    ): String {
        return if (inputExists(year, day) && !forceRefresh) loadInput(year, day)
        else {
            val data = fetchInput(year, day, sessionToken)
            val file = formatInputPath(year, day).toFile()
            file.parentFile.mkdirs()
            file.writeText(data, Charsets.UTF_8)
            data
        }
    }

    fun inputExists(year: Int, day: Int): Boolean {
        validateDate(year, day)
        return formatInputPath(year, day).toFile().exists()
    }

    private fun formatInputPath(year: Int, day: Int): Path {
        return Paths.get(
            formatTemplate(settings.INPUT_SAVE_PATH, year, day),
            formatTemplate(settings.INPUT_SAVE_TEMPLATE, year, day)
        )
    }

    private fun loadInput(year: Int, day: Int): String {
        return formatInputPath(year, day).toFile().readText(Charsets.UTF_8)
    }

    private fun fetchInput(year: Int, day: Int, sessionToken: String): String {
        val url = URL(formatTemplate(settings.INPUT_END_POINT, year, day))
        with(url.openConnection() as HttpsURLConnection) {
            requestMethod = "GET"
            setRequestProperty("Cookie", "session=$sessionToken")

            if (responseCode != 200) {
                throw IllegalArgumentException("No input found for $url")
            }

            return inputStream.readBytes().toString(Charsets.UTF_8).trim()
        }
    }

    fun getAnswers(
        year: Int,
        day: Int,
        forceRefresh: Boolean = false,
        sessionToken: String = settings.SESSION_TOKEN
    ): Pair<String, String> {
        return if (answersExists(year, day) && !forceRefresh) loadAnswers(year, day)
        else {
            val data = fetchAnswers(year, day, sessionToken)
            val file = formatAnswersPath(year, day).toFile()
            file.parentFile.mkdirs()
            file.writeText("${data.first}\n${data.second}", Charsets.UTF_8)
            data
        }
    }

    fun answersExists(year: Int, day: Int): Boolean {
        validateDate(year, day)
        return formatAnswersPath(year, day).toFile().exists()
    }

    private fun formatAnswersPath(year: Int, day: Int): Path {
        return Paths.get(
            formatTemplate(settings.ANSWERS_SAVE_PATH, year, day),
            formatTemplate(settings.ANSWERS_SAVE_TEMPLATE, year, day)
        )
    }

    private fun loadAnswers(year: Int, day: Int): Pair<String, String> {
        val answers = formatAnswersPath(year, day).toFile().readText(Charsets.UTF_8).lines().take(2)
        return answers.first() to answers.last()
    }

    private fun fetchAnswers(year: Int, day: Int, sessionToken: String): Pair<String, String> {
        val url = URL(formatTemplate(settings.ANSWERS_END_POINT, year, day))
        with(url.openConnection() as HttpsURLConnection) {
            requestMethod = "GET"
            setRequestProperty("Cookie", "session=$sessionToken")

            if (responseCode != 200) throw IllegalArgumentException("No input found for $url")

            val body = inputStream.readBytes().toString(Charsets.UTF_8).replace(" ", "").replace("\n", "")
            val answerRegex = """<p>Yourpuzzleanswerwas<code>([^<]+)</code>""".toRegex()

            val answers = answerRegex.findAll(body).take(2).map { it.groupValues[1] }.ifEmpty {
                throw NoAnswerUnlockedException(year, day)
            }
            // FIXME: 07.12.2018 Handle only one answer unlocked

            return answers.first() to answers.last()
        }
    }
}

fun main(args: Array<String>) {
    println(AoC.getInput(2017, 1))
}