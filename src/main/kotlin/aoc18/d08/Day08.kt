package aoc18.d08

import aoc18.util.AoC
import aoc18.util.Day
import aoc18.util.MyAoCSettings

object Day08 : Day<Node>() {

    override fun prepare(input: String): Node {
        val intInput = input.split(" ").map { it.toInt() }
        val root = Node()
        parseNode(root, intInput[0], intInput[1], intInput.subList(2, intInput.size))
        return root
    }

    private fun parseNode(root: Node, children: Int, metaLen: Int, input: List<Int>): Int {
        var cLen = 0
        (0 until children).forEach { _ ->
            val node = Node()
            cLen += parseNode(node, input[cLen], input[cLen + 1], input.subList(2 + cLen, input.size))
            root.children.add(node)
        }
        val meta = input.subList(cLen, cLen + metaLen)
        root.metaData.addAll(meta)
        return 2 + cLen + metaLen
    }

    override fun taskOne(input: Node): String {
        return input.sumMetadata().toString()
    }

    override fun taskTwo(input: Node): String {
        return input.value().toString()
    }
}

fun main(args: Array<String>) {
    AoC.settings = MyAoCSettings
    Day08.runTesting()
}