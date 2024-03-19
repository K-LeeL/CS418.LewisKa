// Assignment 06: SOLID... Functionally This Time
// Due: Mon Mar 18, 2024 11:59pm
// Problem 3: 
// Implement the CPU simulator in Scala.

// https://www.codeconvert.ai/clojure-to-scala-converter

import scala.io.Source

object Problem3 {
    def noop(state: Map[String, Any]): Map[String, Any] = {
        state.updated("cycles", state("cycles").asInstanceOf[Vector[Int]] :+ state("x"))
    }

    def addx(n: Int, state: Map[String, Any]): Map[String, Any] = {
        val x = state("x").asInstanceOf[Int]
        val cycles = state("cycles").asInstanceOf[Vector[Int]]
        state.updated("x", x + n).updated("cycles", cycles ++ Vector(x, x))
    }

    def execute(state: Map[String, Any], lines: List[String]): Map[String, Any] = {
        if (lines.isEmpty) state
        else {
            val line = lines.head
            val nextState: Map[String, Any] = if (line.matches("noop")) noop(state)
                                  else if (line.matches("addx (-?\\d+)")) {
                                      val n = line.split(" ")(1).toInt
                                      addx(n, state)
                                  } else Map.empty[String, Any]
            execute(nextState, lines.tail)
        }
    }

    def executeFile(fileName: String): Vector[Int] = {
        val lines = Source.fromFile(fileName).getLines().toList
        val startingState = Map("x" -> 1, "cycles" -> Vector.empty[Int])
        val endingState = execute(startingState, lines)
        endingState("cycles").asInstanceOf[Vector[Int]]
    }

    def renderCycles(cycles: Vector[Int]): List[String] = {
        def loop(cycles: Vector[Int], screen: String, t: Int): List[String] = {
            if (cycles.isEmpty) screen.grouped(40).toList
            else {
                val x = cycles.head
                val offset = t - x
                val pixel = offset >= -1 && offset <= 1
                val newScreen = screen + (if (pixel) "#" else ".")
                val newT = (t + 1) % 40
                loop(cycles.tail, newScreen, newT)
            }
        }
        loop(cycles, "", 0)
    }

    def printScreen(lines: List[String]): Unit = {
        lines.foreach(println)
    }

    def main(args: Array[String]): Unit = {
        val fileName = "input.txt"
        val cycles = executeFile(fileName)
        val screen = renderCycles(cycles)
        printScreen(screen)
    }
}
