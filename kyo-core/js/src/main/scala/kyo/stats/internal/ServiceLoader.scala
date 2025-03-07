package java.util

import java.util.Iterator
import java.util.ArrayList

class ServiceLoader[T] {
  def iterator(): Iterator[T] =
    (new ArrayList[T](0)).iterator()
}

object ServiceLoader {
  def load[T](cls: Class[T]): ServiceLoader[T] =
    new ServiceLoader[T]
}
