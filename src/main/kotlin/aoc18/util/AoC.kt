package aoc18.util

import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Year
import javax.net.ssl.HttpsURLConnection


object AoC {

    var settings: AoCSettings = DefaultAoCSettings

    fun getInput(year: Int, day: Int, forceRefresh: Boolean = false, sessionToken: String = settings.SESSION_TOKEN): String {
        return if (inputExists(year, day) && !forceRefresh) loadInput(year, day)
        else {
            val data = fetchInput(year, day, sessionToken)
            val file = getInputPath(year, day).toFile()
            file.parentFile.mkdirs()
            file.writeText(data, Charsets.UTF_8)
            data
        }
    }

    fun inputExists(year: Int, day: Int): Boolean {
        validateDate(year, day)
        return getInputPath(year, day).toFile().exists()
    }

    fun validateDate(year: Int, day: Int) {
        require(year > 2015) { "No time travel yet [is: $year, should: > 2015]" }
        require(year <= Year.now().value) { "No time travel yet [is: $year, should: <= ${Year.now()}]" }
        require(day in 1..25) { "Day must be in range of [1-25] is [$day]" }
    }

    private fun formatTemplate(template: String, year: Int, day: Int): String {
        return template.replace("{{year}}", year.toString()).replace("{{day}}", day.toString())
    }

    private fun getInputPath(year: Int, day: Int): Path {
        return Paths.get(formatTemplate(settings.INPUT_SAVE_PATH, year, day), formatTemplate(settings.INPUT_SAVE_TEMPLATE, year, day))
    }

    private fun loadInput(year: Int, day: Int): String {
        return getInputPath(year, day).toFile().readText(Charsets.UTF_8)
    }

    private fun fetchInput(year: Int, day: Int, sessionToken: String): String {
        val url = URL(formatTemplate(settings.INPUT_END_POINT, year, day))
        with(url.openConnection() as HttpsURLConnection) {
            requestMethod = "GET"
            setRequestProperty("Cookie", "session=$sessionToken")
            println("Response Code: $responseCode")

            if (responseCode != 200) {
                throw IllegalArgumentException("No input found for $url")
            }

            return inputStream.readBytes().toString(Charsets.UTF_8).trim()
        }
    }
}

fun main(args: Array<String>) {
    println(AoC.getInput(2017,1))
}