package kyo

import java.io.Closeable

import kyo.core._

import java.util.ArrayList

import resourcesInternal._

sealed abstract class Resources private[kyo] ()
    extends Effect[Resource, Resources] {

  private[kyo] val finalizer: Finalizer < Resources =
    suspend(GetFinalizer.asInstanceOf[Resource[Finalizer]])

  def ensure(v: => Unit < IOs): Unit < (IOs with Resources) =
    finalizer.map(_.put(IOs(v)))

  def acquire[T <: Closeable](resource: => T): T < (IOs with Resources) = {
    lazy val v = resource
    ensure(v.close()).andThen(v)
  }

  def run[T, S](v: T < (Resources with S))(
      implicit f: Flat[T < (Resources with S)]
  ): T < (IOs with S) = {
    val finalizer = new Finalizer
    implicit def handler: Handler[Resource, Resources, Any] =
      new Handler[Resource, Resources, Any] {
        def pure[U](v: U) = v
        def apply[U, V, S2](
            m: Resource[U],
            f: U => V < (Resources with S2)
        ): V < (S2 with Resources) =
          m match {
            case GetFinalizer =>
              f(finalizer.asInstanceOf[U])
            case _ =>
              f(m.asInstanceOf[U])
          }
      }
    IOs.ensure(finalizer.run) {
      handle[T, Resources with S, Any](v).asInstanceOf[T < S]
    }
  }
}
object Resources extends Resources

private[kyo] object resourcesInternal {
  type Resource[T] >: T // = T | GetFinalizer

  private[kyo] case object GetFinalizer
  private[kyo] class Finalizer extends ArrayList[Unit < IOs] {
    def put(close: Unit < IOs): Unit < IOs =
      IOs {
        add(close)
        ()
      }
    val run: Unit < IOs =
      IOs {
        while (size() > 0) IOs.run(remove(0))
      }
  }

}
