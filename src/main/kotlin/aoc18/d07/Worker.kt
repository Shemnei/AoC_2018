package aoc18.d07

data class Worker(val id: Int = GLOB_ID, var time: Int = 0, var task: Char? = null) {

    companion object {
        private var GLOB_ID: Int = 0
    }

    init {
        GLOB_ID = id + 1
    }
}