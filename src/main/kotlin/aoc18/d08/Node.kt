package aoc18.d08

data class Node(
    val id: Int = GLOB_ID,
    val metaData: MutableList<Int> = mutableListOf(),
    val children: MutableList<Node> = mutableListOf()
) {

    companion object {
        private var GLOB_ID: Int = 0
    }

    init {
        GLOB_ID = id + 1
    }

    fun sumMetadata(): Int {
        return metaData.sum() + children.sumBy(Node::sumMetadata)
    }

    fun value(): Int {
        return if (children.isEmpty())
            metaData.sum()
        else
            metaData.mapNotNull { meta -> children.getOrNull(meta - 1) }.sumBy(Node::value)
    }
}