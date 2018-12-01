package aoc18.util

fun <T> Iterable<T>.cycle(): Sequence<T> {
    return object : Sequence<T> {
        override fun iterator(): Iterator<T> {
            return object : Iterator<T> {

                private var iter = this@cycle.iterator()

                override fun hasNext(): Boolean {
                    if (!iter.hasNext())
                        iter = this@cycle.iterator()
                    return true
                }

                override fun next(): T {
                    return iter.next()
                }

            }
        }
    }
}

fun Sequence<Int>.accumulate(): Sequence<Int> {
    return object : Sequence<Int> {
        override fun iterator(): Iterator<Int> {
            return object : Iterator<Int> {

                private var sum: Int = 0
                private var iter = this@accumulate.iterator()

                override fun hasNext() = this.iter.hasNext()

                override fun next(): Int {
                    sum += iter.next()
                    return sum
                }

            }
        }
    }
}