package kyo

import scala.quoted._

object Trees {
  def traverse(using
      Quotes
  )(tree: quotes.reflect.Tree)(pf: PartialFunction[quotes.reflect.Tree, Unit]): Unit = {
    import quotes.reflect._
    (new TreeTraverser {
      override def traverseTree(tree: Tree)(owner: Symbol): Unit =
        pf.lift(tree).getOrElse(super.traverseTree(tree)(owner))
    }).traverseTree(tree)(Symbol.spliceOwner)
  }

  def transform(using
      Quotes
  )(tree: quotes.reflect.Tree)(pf: PartialFunction[quotes.reflect.Tree, quotes.reflect.Term])
      : quotes.reflect.Tree = {
    import quotes.reflect._
    (new TreeMap {
      override def transformTerm(tree: Term)(owner: Symbol): Term =
        pf.lift(tree).getOrElse(super.transformTerm(tree)(owner))
    }).transformTree(tree)(Symbol.spliceOwner)
  }

  def exists(using
      Quotes
  )(tree: quotes.reflect.Tree)(pf: PartialFunction[quotes.reflect.Tree, Boolean]) = {
    var r = false
    traverse(tree) {
      case t if pf.isDefinedAt(t) && !r => r = pf(t)
    }
    r
  }
}
