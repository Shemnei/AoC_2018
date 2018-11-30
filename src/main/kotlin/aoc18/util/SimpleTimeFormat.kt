package aoc18.util

import klog.LogRecord
import klog.format.LogFormat
import java.time.format.DateTimeFormatter

class SimpleTimeFormat : LogFormat {
    private val DT_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")

    override fun format(log: LogRecord): String {
        return "[%s] [%s/%s] - %s".format(log.created.format(DT_FORMAT), log.loggerId, log.level, log.message)
    }
}