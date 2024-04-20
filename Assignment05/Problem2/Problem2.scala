// Assignment 05: Monads for you, monads for me
// Due: Mon Mar 11, 2024 11:59pm
// Kaylee Lewis

// Problem 2:
//      Consider the following Scala object that is trying to produce the unit function 
//      (function that lifts the value in the monadic context) to build a form of lazy value:
//         
//          object Lazy 
//          {
//          def apply[A](value: => A): Lazy[A] = new Lazy(value)
//          def flatMap[B](f: (=> A) => Lazy[B]): Lazy[B] = f(internal)
//          def map[B](f: A => B): Lazy[B] = flatMap(x => Lazy(f(x)))
//          }
//    
//      We use to add the effect of lazy initialization to a value:
//
//          val lazyInt: Lazy[Int] = Lazy 
//          {
//          println("The response to everything is 42")
//          42
//          }
//
//      This forms a monad. So… show how this object satisifies the three laws of monadicity: left identity, right identity, and associativity.

// www.geeksforgeeks.org/scala-lazy-initialization/
// www.scala-lang.org/api/2.12.3/scala/Function1.html

object Lazy 
{
    // Apply method to create a new Lazy object
    def apply[A](value: => A): Lazy[A] = new Lazy(value)
}

class Lazy[+A](value: => A) 
{
    // Internal value
    private lazy val internal: A = value

    // FlatMap and Map methods
    def flatMap[B](f: A => Lazy[B]): Lazy[B] = f(internal)
    def map[B](f: A => B): Lazy[B] = Lazy(f(internal))

    // Expose the internal value
    def getValue: A = internal
}

object Main 
{
    def main(args: Array[String]): Unit = 
    {
        // Left Identity
        val x = 42
        def f(x: Int): Lazy[Int] = Lazy(x * 2)
        val leftIdentityResult = Lazy(x).flatMap(f)
        val fAppliedDirectly = f(x)
        println("Left Identity: " + (leftIdentityResult.getValue == fAppliedDirectly.getValue))

        // Right Identity
        val m: Lazy[Int] = Lazy {
        println("The response to everything is 42")
        42
        }

        val rightIdentityResult = m.flatMap(Lazy.apply)
        println("Right Identity: " + (rightIdentityResult.getValue == m.getValue))

        // Associativity
        def g(x: Int): Lazy[Int] = Lazy(x + 3)
        val associativityLeft = m.flatMap(f).flatMap(g)
        val associativityRight = m.flatMap(x => f(x).flatMap(g))
        println("Associativity: " + (associativityLeft.getValue == associativityRight.getValue))
  }
}
