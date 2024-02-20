// Assignment 04: Getting Started with Scala
// Due: Mon Feb 19, 2024 11:59pm
// Kaylee Lewis

// Problem 1:
// In the *Functional Design” text, Uncle Bob provides an implementation of the Bowling Game kata in both Java and Clojure.
// Using Uncle Bob’s Clojure pragram as a guide, implement the Bowling Game kata in Scala.

// http://butunclebob.com/ArticleS.UncleBob.TheBowlingGameKata
// https://kata-log.rocks/bowling-game-kata
// https://codereview.stackexchange.com/questions/162679/bowling-game-kata-in-scala-pattern-match
// https://qualityswdev.com/2011/03/14/the-bowling-game-kata-in-scala/
// https://www.scala-exercises.org/std_lib/pattern_matching
// https://www.scala-exercises.org/std_lib/lists

import scala.annotation.tailrec

object BowlingGame
{
  // Function to calculate the score of the bowling game
  def score(rolls: List[Int]): Int =
  {
    // Funciton to calculate the score of the game
    @tailrec
    def scoreAcc(rolls: List[Int], frame: Int, acc: Int): Int =
    {
      // Check if the game is over
      if (frame == 11) acc
      // Check if the current frame is a strike, spare, or something
      else rolls match
      {
        // If the current frame is a strike, add 10 to the score and the strike bonus
        case Nil => acc
        case 10 :: rest => scoreAcc(rest, frame + 1, acc + 10 + strikeBonus(rolls))

        // If the current frame is a spare, add 10 to the score and the spare bonus
        case first :: second :: rest if first + second == 10 => scoreAcc(rest, frame + 1, acc + 10 + spareBonus(rest))

        // If the current frame is neither a strike nor a spare, add the sum of the two rolls to the score
        case first :: second :: rest => scoreAcc(rest, frame + 1, acc + first + second)
        case _ => acc
      }
    }

    // Call the helper function with the initial frame and score
    scoreAcc(rolls, 1, 0)
  }

  // Function to calculate the strike bonus
  def strikeBonus(rolls: List[Int]): Int = rolls match
  {
    // If the list is empty, return 0
    case Nil => 0

    // If the first element is a strike, add 10 to the score and the next two rolls
    case x :: xs => x + strikeBonus(xs)
  }

  // Function to calculate the spare bonus
  def spareBonus(rolls: List[Int]): Int = rolls match
  {
    // If the list is empty, return 0
    case Nil => 0

    // If the first element is a spare, add 10 to the score and the next roll
    case x :: xs => x
  }
}

// Test the functions
object Main extends App
{
  val exampleGame = List(10, 3, 7, 6, 4, 10, 10, 10, 2, 8, 9, 1, 10, 10, 10)
  println("Example Game Score: " + BowlingGame.score(exampleGame))
}
