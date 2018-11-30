package aoc18.util

tailrec fun gcd(x: Long, y: Long): Long = if (y == 0L) x else gcd(y, x % y)
fun agcd(x: Long, y: Long) = gcd(Math.abs(x), Math.abs(y))