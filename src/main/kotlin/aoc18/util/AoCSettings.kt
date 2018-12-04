package aoc18.util

interface AoCSettings {
    val SESSION_TOKEN: String

    val INPUT_SAVE_PATH: String
    val INPUT_SAVE_TEMPLATE: String
    val INPUT_END_POINT: String

    val ANSWERS_SAVE_PATH: String
    val ANSWERS_SAVE_TEMPLATE: String
    val ANSWERS_END_POINT: String
}

