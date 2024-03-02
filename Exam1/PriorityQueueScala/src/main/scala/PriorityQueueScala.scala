//Purpose:
// The priority queue data type works like a regular queue with two additional invariants:
// the contents of the queue are stored in some sorted order, and popping an item off the
// priority queue returns the item with the highest priority rather than the item at the head
// of the queue.
// Inserts will place the new item based upon its priority.
// Define data types in Scala that implement this form of a priority queue. 

// https://www.scala-lang.org/api/2.12.6/scala/collection/mutable/PriorityQueue.html
// https://www.baeldung.com/scala/priorityqueue

import scala.collection.mutable.PriorityQueue

// Scala program to implement a priority queue
class PriorityQueueScala [ A ] ( implicit ordering : Ordering [ A ] )
{

  // Creating an empty priority queue
  private val pq = PriorityQueue.empty [ A ] ( ordering )

  // Function to add an element to the priority queue
  def push ( item: A ) : Unit = pq.enqueue ( item )

  // Function to remove an element from the priority queue
  def pop ( ) : Option [ A ] =
  {
    // If the priority queue is not empty, then remove the element
    if ( pq.nonEmpty ) Some ( pq.dequeue ( ) )

    // If the priority queue is empty, then return None
    else None
  }

  // Function to check if the priority queue is empty
  def traverse ( ) : Unit =
  {
    // If the priority queue is not empty, then traverse the elements
    val sortedList = pq.clone ( ).dequeueAll

    // Print the elements of the priority queue
    println ( sortedList.mkString ( ", " ) )
  }
}

object Main
{
  def main(args: Array[String]): Unit =
  {
    // Creating a priority queue
    val pq = new PriorityQueueScala[Int]()
    // Adding elements to the priority queue
    pq.push(3)
    pq.push(1)
    pq.push(2)
    pq.push(5)
    pq.push(4)

    // Traversing the priority queue
    println("Traversing the priority queue:")
    pq.traverse()

    // Popping elements from the priority queue
    println("Popping items from the priority queue:")

    // Popping the elements from the priority queue
    while (pq.pop().isDefined)
    {
      // Traversing the priority queue
      pq.traverse()
    }
  }
}
// In the Scala implementation:
// PriorityQueue uses a specified ordering.
// The push method adds an item to the queue.
// The pop method removes and returns the highest-priority item.
// The traverse method prints the items in priority order.
