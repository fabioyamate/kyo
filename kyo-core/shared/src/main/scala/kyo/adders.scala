package kyo

import kyo._

import java.util.concurrent.atomic.{DoubleAdder => JDoubleAdder}
import java.util.concurrent.atomic.{LongAdder => JLongAdder}

object Adders {
  val initLong: LongAdder < IOs     = IOs(new LongAdder(new JLongAdder()))
  val initDouble: DoubleAdder < IOs = IOs(new DoubleAdder(new JDoubleAdder()))
}

class LongAdder private[kyo] (private val ref: JLongAdder) extends AnyVal {

  def add(v: Long): Unit < IOs = IOs(ref.add(v))
  def decrement: Unit < IOs    = IOs(ref.decrement())
  def increment: Unit < IOs    = IOs(ref.increment())
  def get: Long < IOs          = IOs(ref.sum())
  def reset: Unit < IOs        = IOs(ref.reset())
}

class DoubleAdder private[kyo] (private val ref: JDoubleAdder) extends AnyVal {

  def add(v: Double): Unit < IOs = IOs(ref.add(v))
  def get: Double < IOs          = IOs(ref.sum())
  def reset: Unit < IOs          = IOs(ref.reset())
}
