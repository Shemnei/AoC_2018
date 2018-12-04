package aoc18.util

object DefaultAoCSettings : AoCSettings {
    override val SESSION_TOKEN: String = ""

    override val INPUT_SAVE_PATH: String = "input_{{year}}"
    override val INPUT_SAVE_TEMPLATE: String = "input_d{{day}}.txt"
    override val INPUT_END_POINT: String = "https://adventofcode.com/{{year}}/day/{{day}}/input"

    override val ANSWERS_SAVE_PATH: String = "answers_{{year}}"
    override val ANSWERS_SAVE_TEMPLATE: String = "answers_d{{day}}.txt"
    override val ANSWERS_END_POINT: String = "https://adventofcode.com/{{year}}/day/{{day}}"
}