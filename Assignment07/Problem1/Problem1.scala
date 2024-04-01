// Assignment 07: Concurrent sorting
// Due: Mon Apr 1, 2024 11:59pm 
// Problem 1: 

// On the surface, the Quicksort algorithm would appear to be a good candidate for parallelization as the sorting of the left 
// and right hand sides of the partitioned container work on independent sublists. 
// But attempts to use Scala Futures to do that parallelization often fail.
// See if you can the following code to run in your environment, report on the results, and see if you can explain the results:

// https://docs.scala-lang.org/overviews/core/futures.html
// https://www.scala-lang.org/api/current/scala/util/Sorting$.html
// https://blog.genuine.com/2018/08/a-brief-overview-of-scala-futures/
// https://medium.com/nerd-for-tech/futures-in-scala-201677bc5d97

import scala.concurrent.Future // more of a standard for working with futures in Scala
import scala.concurrent.Await 
import scala.concurrent.duration.Duration.Inf // used to ensure that the Await.result does not timeout prematurely.
import scala.concurrent.ExecutionContext.Implicits.global // commonly used when working with Scala futures
import scala.collection.mutable._
import scala.util.Random

// Implements the quicksort algorithm in a non-parallel way
object QuickSort 
{
    def sort(a: Array[Int]): Array[Int] = 
    {
        if (a.length < 2) return a
        val pivot = a(a.length / 2)
        sort(a.filter(_ < pivot)) ++ a.filter(_ == pivot) ++ sort(a.filter(_ > pivot))
    }
}

// Implements the quicksort algorithm in a parallel way
object QuickSortFutures 
{
    def sort(a: Array[Int]): Array[Int] = 
    {
        if (a.length < 2) return a

        // Split the array into three parts that are less than, equal to, and greater than the pivot
        val pivot = a(a.length / 2)
        val less = Future { sort(a.filter(_ < pivot)) }
        val equal = Future { a.filter(_ == pivot) }
        val more = Future { sort(a.filter(_ > pivot)) }

        // Await is used to block the current thread until the result is available
        // Await the results of the three parts and concatenate them
        Await.result(less, Inf) ++ Await.result(equal, Inf) ++ Await.result(more, Inf)
  }
}

object Main 
{
    // Initialize an array with random numbers
    def initArrayWith(limit: Int): Array[Int] = 
    {
        // Create a list buffer and fill it with random numbers
        val list: ListBuffer[Int] = new ListBuffer()

        // Create a random number generator
        val randGen = new Random()

        // Fill the list buffer with random numbers
        (0 until limit).foreach(i => list += randGen.nextInt(1000000))
        list.toArray
    }
    
    // Main method takes a array of strings as an argument
    def main(args: Array[String]): Unit = 
    {
        // Initialize an array with 200 random numbers
        val a = initArrayWith(200)

        //  Get the current time in milliseconds
        val t1 = System.currentTimeMillis 

        //  Sort the array using the non-parallel quicksort algorithm
        QuickSort.sort(a) 

        //  Get the current time in milliseconds
        val t2 = System.currentTimeMillis 

        //  Sort the array using the parallel quicksort algorithm
        QuickSortFutures.sort(a)  

        //  Get the current time in milliseconds
        val t3 = System.currentTimeMillis  

        //  Print the time taken for the non-parallel quicksort algorithm
        println("NonParallel = " + (t2 - t1))

        //  Print the time taken for the parallel quicksort algorithm  
        println("Parallel = " + (t3 - t2))  
    }
}

// The output shows that the non-parallel version is significantly faster than the parallel version. 
// This is because the workload is not as evenly distributed among the parallel tasks. 
// If the array is not evenly partitioned around the pivot, some subtasks might take longer to complete than others, leading to an inefficient parallelism.