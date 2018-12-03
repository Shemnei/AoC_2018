package aoc18.d03

data class Rectangle(val x1: Int, val x2: Int, val y1: Int, val y2: Int) {

    companion object {
        fun of(marginLeft: Int, marginTop: Int, width: Int, height: Int): Rectangle {
            return Rectangle(marginLeft, marginLeft + width, marginTop, marginTop + height)
        }
    }

    val width = x2 - x1
    val height = y2 - y1

    val area = width * height

    fun intersection(other: Rectangle): Rectangle? {
        val _x1 = Math.max(this.x1, other.x1)
        val _x2 = Math.min(this.x2, other.x2)
        val _y1 = Math.max(this.y1, other.y1)
        val _y2 = Math.min(this.y2, other.y2)
        return Rectangle(_x1, _x2, _y1, _y2).takeIf { _x1 < _x2 && _y1 < _y2 }
    }

    fun intersectionArea(other: Rectangle): Int {
        return this.intersection(other)?.area ?: 0
    }
}