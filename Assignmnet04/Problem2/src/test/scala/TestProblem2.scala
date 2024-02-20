import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
// Test Tree
class TestProblem2 extends AnyFlatSpec
{
  import Tree._

  // Test if empty
  "An empty Tree" should "be empty" in
    {
      assert(empty(Empty))
    }

  // Test member
  it should "not contain any elements" in
    {
      assert(!member(Empty, 5))
    }

  // Test in-order traversal
  it should "have no in-order traversal" in
    {
      assert(inOrder(Empty)(_ => ()) === Nil)
    }

  // Test pre-order traversal
  it should "have no pre-order traversal" in
    {
      assert(preOrder(Empty)(_ => ()) === Nil)
    }

  // Test post-order traversal
  it should "have no post-order traversal" in
    {
      assert(postOrder(Empty)(_ => ()) === Nil)
    }

  // Test insert
  "A Tree" should "insert elements correctly" in
    {
      val tree = insert(insert(insert(empty[Int], 5), 3), 7)
      assert(member(tree, 5))
      assert(member(tree, 3))
      assert(member(tree, 7))
    }

  // Test delete
  it should "delete elements correctly" in
    {
      val tree = insert(insert(insert(empty[Int], 5), 3), 7)
      val newTree = delete(tree, 3)
      assert(!member(newTree, 3))
    }

  // Test in-order traversal
  it should "traverse in-order correctly" in
    {
      val tree = insert(insert(insert(empty[Int], 5), 3), 7)
      assert(inOrder(tree)(identity) === List(3, 5, 7))
    }

  // Test pre-order traversal
  it should "traverse pre-order correctly" in
    {
      val tree = insert(insert(insert(empty[Int], 5), 3), 7)
      assert(preOrder(tree)(identity) === List(5, 3, 7))
    }

  // Test post-order traversal
  it should "traverse post-order correctly" in
    {
      val tree = insert(insert(insert(empty[Int], 5), 3), 7)
      assert(postOrder(tree)(identity) === List(3, 7, 5))
    }
}
