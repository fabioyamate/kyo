package kyo

import izumi.reflect._

import scala.reflect.ClassTag

import kyo.core._

object Envs {
  private case object Input

  type Env[E] = {
    type Value[T] >: T // = T | Input.type
  }

  def apply[E](implicit tag: Tag[E]): Envs[E] =
    new Envs[E]
}
import Envs._

final class Envs[E] private[kyo] (implicit private val tag: Tag[_])
    extends Effect[Env[E]#Value, Envs[E]] { self =>

  val get: E < Envs[E] =
    suspend(Input.asInstanceOf[Env[E]#Value[E]])

  def run[T, S](e: E)(v: T < (Envs[E] with S))(implicit f: Flat[T < (Envs[E] with S)]): T < S = {
    implicit val handler: Handler[Env[E]#Value, Envs[E], Any] =
      new Handler[Env[E]#Value, Envs[E], Any] {
        def pure[U](v: U) = v
        def apply[U, V, S2](
            m: Env[E]#Value[U],
            f: U => V < (Envs[E] with S2)
        ): V < (S2 with Envs[E]) =
          m match {
            case Input =>
              f(e.asInstanceOf[U])
            case _ =>
              f(m.asInstanceOf[U])
          }
      }
    handle[T, Envs[E] with S, Any](v).asInstanceOf[T < S]
  }

  override def accepts[M2[_], E2 <: Effect[M2, E2]](other: Effect[M2, E2]) =
    other match {
      case other: Envs[_] =>
        other.tag.tag == tag.tag
      case _ =>
        false
    }

  override def toString = s"Envs[${tag.tag.longNameWithPrefix}]"

  def layer[Sd](construct: E < Sd): Layer[Envs[E], Sd] =
    new Layer[Envs[E], Sd] {
      override def run[T, S](effect: T < (Envs[E] with S))(implicit
          fl: Flat[T < (Envs[E] with S)]
      ): T < (Sd with S) =
        construct.map(e => self.run[T, S](e)(effect))
    }
}
