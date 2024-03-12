// Assignment 05: Monads for you, monads for me
// Due: Mon Mar 11, 2024 11:59pm
// Kaylee Lewis

// Problem 1: Purity, sort of
//      We have shown in lecture a method that allows us to separate an impure function into 
//      two functions: 
//          a pure function that generates a description of what must be done and impure function
//          that interprets and evaluates that description of the unsafe action. 
//          We demonstrated how to use this scheme with Quicksort (as it’s an in-place sort). 
//          Do the same in Scala for the insertion sort algorithm.
//
// https://www.scala-lang.org/api/2.12.3/scala/collection/immutable/List.html
// https://www.scala-lang.org/api/2.12.3/scala/math/Ordering.html
// https://www.geeksforgeeks.org/insertion-sort/
// https://www.geeksforgeeks.org/pure-function-in-scala/?ref=header_search
// https://www.geeksforgeeks.org/functional-programming-pure-and-impure-functions/

object Main extends App
{
    // Pure function: Inserts an element into a sorted list
    def pureInsertElement[A](element: A, sortedList: List[A])(implicit ordering: Ordering[A]): List[A] = sortedList match 
    {
        case Nil => List(element)
        case head :: tail =>
        if (ordering.lt(element, head)) 
        {
            element :: sortedList
        } else 
        {
            head :: pureInsertElement(element, tail)
        }
    }
    
    // Pure function: Sorts a list using insertion sort
    def pureSortList[A](unsortedList: List[A], sortedAccumulator: List[A])(implicit ordering: Ordering[A]): List[A] = unsortedList match 
    {
        case Nil => sortedAccumulator
        case head :: tail => pureSortList(tail, pureInsertElement(head, sortedAccumulator))
    }
    
    // Impure function: Orchestrates the sorting process and interacts with the outside world
    def impureInsertionSort[A](inputList: List[A])(implicit ordering: Ordering[A]): List[A] = 
    {
        pureSortList(inputList, Nil)
    }
    
    // Test function
    def testInsertionSort(): Unit =
    {
        val unsortedList = List(4, 3, 2, 10, 12, 1, 5, 6)
        println("Original List:")
        println("\t" + unsortedList)
       
        val sortedList = List(1, 2, 3, 4, 5, 6, 10, 12)
        println("Sorted List:")
        println("\t" + sortedList)

        val reverseSortedList = List(12, 10, 6, 5, 4, 3, 2, 1)
        println("Reverse Sorted List:")
        println("\t" + reverseSortedList)

        val listWithDuplicates = List(12, 10, 6, 5, 4, 10, 12)
        println("List with Duplicates:")
        println("\t" + impureInsertionSort(listWithDuplicates))
    }

    // Run the tests
    testInsertionSort()
}
