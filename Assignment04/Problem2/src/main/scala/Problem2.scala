// Assignment 04: Getting Started with Scala
// Due: Mon Feb 19, 2024 11:59pm
// Kaylee Lewis

// Problem 2: Treeing It Up
// The Tree abstract datatype can be recursively defined using lists.
// We will define a tat as a tree of atoms, where we will denote an empty tree as being the empty list ()
// or as a list (atom leftChild RightChild) where leftChild and rightChild are trees of atoms that are the
// respective left and right children.
//
// Assuming that we have the ability to partial order items
// (i.e., we have the ability to use the >, ’<, and==` operators on atoms),
// implement this data type in Racket, assuming you include:
//
//    (insert tat atom): insert an atom into a tree,
//    (delete tat atom): delete the the first occurrence of an atom from a tree,
//    (empty? tat atom): predicate to test to see if a tree of atoms is empty,
//    (member? tat atom): predicate to check to see if an atom is in a tree of atoms
//    (in-order tat exp): traverse a tree of atoms in ‘in-order fashion’ applying an expression
//        to each node as you go and building a list of results.
//    (pre-order tat exp): traverse a tree of atoms in ‘pre-order fashion’ applying an expression
//        to each node as you go and building a list of results.
//    (post-order tat exp): traverse a tree of atoms in ‘post-order fashion’ applying an expression
//        to use node in the tree.
//
// Implement this data type in Scala.
// Use the ScalaTest framework to test your data type.
//
// https://www.scalatest.org/quick_start
// https://docs.scala-lang.org/overviews/reflection/symbols-trees-types.html
// https://scalameta.org/docs/trees/guide.html
// https://www.scala-lang.org/api/2.12.0/scala/collection/immutable/List.html
// https://www.scalatest.org/scaladoc/1.4.1/org/scalatest/FlatSpec.html

sealed trait Tree[+A]
case object Empty extends Tree[Nothing]
case class Node[A](value: A, left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {
  // Create an empty tree
  def empty[A]: Tree[A] = Empty

  // Insert an atom
  def insert[A](tat: Tree[A], atom: A)(implicit ord: Ordering[A]): Tree[A] = tat match {
    // If empty, create a new node with the atom
    case Empty => Node(atom, Empty, Empty)
    // If the tree is not empty, compare the atom to the value of the node
    case Node(value, left, right) =>
      if (ord.equiv(atom, value)) tat
      else if (ord.lt(atom, value)) Node(value, insert(left, atom), right)
      else Node(value, left, insert(right, atom))
  }

  // Delete the first atom
  def delete[A](tat: Tree[A], atom: A)(implicit ord: Ordering[A]): Tree[A] = tat match {
    // If empty, return empty
    case Empty => Empty
    // If the tree is not empty, compare the atom to the value of the node
    case Node(value, left, right) =>
      // If the atom is less than the value, delete the atom from the left
      if (ord.lt(atom, value)) Node(value, delete(left, atom), right)
      // If the atom is greater than the value, delete the atom from the right
      else if (ord.gt(atom, value)) Node(value, left, delete(right, atom))
      else {
        // If equal, delete the node
        if (left == Empty) right
        else if (right == Empty) left
        else {
          // Find the min value in the right subtree
          val successor = findMin(right)
          Node(successor, left, delete(right, successor))
        }
      }
  }

  // Find the minimum value
  private def findMin[A](tree: Tree[A]): A = tree match {
    // If empty, throw an exception
    case Node(value, Empty, _) => value
    case Node(_, left, _) => findMin(left)
    case Empty => throw new NoSuchElementException("Empty tree")
  }

  // Check if empty
  def empty[A](tat: Tree[A]): Boolean = tat == Empty

  // Check if the atom is a member
  def member[A](tat: Tree[A], atom: A)(implicit ord: Ordering[A]): Boolean = tat match {
    case Empty => false
    case Node(value, left, right) =>
      if (ord.equiv(atom, value)) true
      else if (ord.lt(atom, value)) member(left, atom)
      else member(right, atom)
  }

  // Traverse in-order
  def inOrder[A, B](tat: Tree[A])(exp: A => B): List[B] = tat match {
    case Empty => Nil
    case Node(value, left, right) => inOrder(left)(exp) ::: List(exp(value)) ::: inOrder(right)(exp)
  }

  // Traverse pre-order
  def preOrder[A, B](tat: Tree[A])(exp: A => B): List[B] = tat match {
    case Empty => Nil
    case Node(value, left, right) => List(exp(value)) ::: preOrder(left)(exp) ::: preOrder(right)(exp)
  }

  // Traverse post-order
  def postOrder[A, B](tat: Tree[A])(exp: A => B): List[B] = tat match {
    case Empty => Nil
    case Node(value, left, right) => postOrder(left)(exp) ::: postOrder(right)(exp) ::: List(exp(value))
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    // Test Tree
    val tree = Tree.insert(Tree.insert(Tree.insert(Tree.empty[Int], 5), 3), 7)
    println("Original tree:")
    println("In-order traversal: " + Tree.inOrder(tree)(identity))
    println("Pre-order traversal: " + Tree.preOrder(tree)(identity))
    println("Post-order traversal: " + Tree.postOrder(tree)(identity))

    // Test insert
    val newTree = Tree.delete(tree, 3)
    println("\nTree after deleting 3:")
    println("In-order traversal: " + Tree.inOrder(newTree)(identity))
  }
}