// Assignment 07: Concurrent sorting
// Due: Mon Apr 1, 2024 11:59pm 
// Problem 2: 

// Using the parallel version of Quicksort as inspiration, see if you implement a parallel version of Mergesort using futures. 
// Then, time the difference in performance (if anY) between your parallel Mergesort and the functional version above.

// www.scala-lang.org/api/2.12.3/scala/concurrent/Future.html
// https://medium.com/analytics-vidhya/playing-with-scala-merge-sort-d382fb1a32ff
// https://www.geeksforgeeks.org/merge-sort/
// https://mccricardo.com/merge-sort-in-scala/

import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random
import scala.annotation.tailrec

object MergeSort 
{
  // Function for performing functional merge sort
  def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = 
  {
    // Function for merging two sorted lists
    @tailrec
    def merge(res: List[T], xs: List[T], ys: List[T]): List[T] = (xs, ys) match 
    {
      // If one list is empty, append the other list to the result
      case (_, Nil) => res.reverse ::: xs
      case (Nil, _) => res.reverse ::: ys
      // Compare the heads of both lists and merge accordingly
      case (x :: xs1, y :: ys1) =>
        if (ord.lt(x, y)) merge(x :: res, xs1, ys)
        else merge(y :: res, xs, ys1)
    }

    // Calculate the midpoint of the list
    val n = xs.length / 2

    // If the list is empty or contains only one element, return the list
    if (n == 0) xs
    else {
      // Split the list into two halves
      val (ys, zs) = xs splitAt n

      // Recursively sort each half
      val sortedLeft = msort(ys)
      val sortedRight = msort(zs)

      // Merge the sorted halves
      merge(Nil, sortedLeft, sortedRight)
    }
  }
}

object ParallelMergeSort 
{
  // Function for performing parallel merge sort
  def msortParallel[T](xs: List[T])(implicit ord: Ordering[T]): Future[List[T]] = 
  {
    // Function for merging two sorted lists
    @tailrec
    def merge(res: List[T], xs: List[T], ys: List[T]): List[T] = (xs, ys) match 
    {
      // If one list is empty, append the other list to the result
      case (_, Nil) => res.reverse ::: xs
      case (Nil, _) => res.reverse ::: ys
      // Compare the heads of both lists and merge accordingly
      case (x :: xs1, y :: ys1) =>
        if (ord.lt(x, y)) merge(x :: res, xs1, ys)
        else merge(y :: res, xs, ys1)
    }

    // Calculate the midpoint of the list
    val n = xs.length / 2

    // If the list is empty or contains only one element, return a successful future
    if (n == 0) Future.successful(xs)
    else {
      // Split the list into two halves
      val (ys, zs) = xs splitAt n

      // Recursively sort each half in parallel
      val leftFuture = msortParallel(ys)
      val rightFuture = msortParallel(zs)

      // Merge the sorted halves when both futures are completed
      for {
        left <- leftFuture
        right <- rightFuture
      } yield merge(Nil, left, right)
    }
  }
}

object Main {
  def main(args: Array[String]): Unit = 
  {
    // Generate a random list of integers
    val list = Seq.fill(50000)(Random.nextInt(500)).toList
    val t1 = System.currentTimeMillis()

    // Perform functional merge sort and measure the time taken
    val sortedListFunc = MergeSort.msort(list)
    val t2 = System.currentTimeMillis()
    println("Functional MergeSort took: " + (t2 - t1) + " milliseconds")

    // Perform parallel merge sort and measure the time taken
    val t3 = System.currentTimeMillis()
    val resultFuture = ParallelMergeSort.msortParallel(list)
    val sortedListParallel = resultFuture.map { result =>
      val t4 = System.currentTimeMillis()
      println("Parallel MergeSort took: " + (t4 - t3) + " milliseconds")
      result
    }

    // Block until the result is available
    Await.ready(sortedListParallel, Duration.Inf)
  }
}
