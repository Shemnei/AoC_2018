package aoc18.d07

import java.util.*

data class WorkerQueue(val workerCount: Int, val input: Map<Char, MutableList<Char>>) {

    private val workers = (0 until workerCount).map { Worker() }.toTypedArray()
    private val inputCopy = HashMap(input)
    private val isDone: Boolean
        get() = inputCopy.isEmpty()
//        get() = inputCopy.isEmpty() && workers.all { it.task == null }


    private var totalTime = 0

    fun run(): Int {
        while (doCycle()) {
        }

        totalTime += workers.filter { it.task != null && it.time > 0 }.maxBy { it.time }?.time ?: 0
        workers.forEach { it.task = null; it.time = 0 }

        return totalTime
    }

    private fun doCycle(): Boolean {
        if (isDone) return false

        val freeWorker = workers.firstOrNull { it.time == 0 && it.task == null }
        if (freeWorker == null) {
            waitForWorker()
            return true
        }

        val task = inputCopy.filter { it.value.isEmpty() }.minBy { it.key }?.key
        if (task == null) {
            waitForWorker()
            return true
        }

        inputCopy.remove(task)
        freeWorker.task = task
        freeWorker.time = 61 + (task - 'A')
        return true
    }

    private fun waitForWorker() {
        val shortestTimeWorker = workers.filter { it.time > 0 }.minBy { it.time }!!
        val sleepTime = shortestTimeWorker.time

        workers.forEach { w -> w.time = Math.max(w.time - sleepTime, 0) }
        freeWorkers()

        totalTime += sleepTime
    }

    private fun freeWorkers() {
        workers.filter { it.time == 0 && it.task != null }.forEach { w ->
            inputCopy.mapValues { it.value.apply { remove(w.task) } }
            w.task = null
        }
    }
}