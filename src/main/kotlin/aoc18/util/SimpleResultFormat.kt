package aoc18.util

import klog.LogRecord
import klog.format.LogFormat

class SimpleResultFormat : LogFormat {
    override fun format(log: LogRecord): String {
        return "%s %s - %s".format(log.level.toString().subSequence(0, 3), log.loggerId, log.message)
    }
}