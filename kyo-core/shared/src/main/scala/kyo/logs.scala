package kyo

import org.slf4j.LoggerFactory

import scala.languageFeature.implicitConversions

object Logs {

  private val logger = LoggerFactory.getLogger("kyo.logs")

  /*inline*/
  def trace( /*inline*/ msg: => String)(
      implicit
      file: sourcecode.FileName,
      line: sourcecode.Line
  ): Unit < IOs = {
    // Discard wrappers so their allocation
    // can be elided by the JIT. The values
    // are `String`s from the constant pool
    // since they're generated by a macro.
    // This could be avoided if sourcecode's
    // values were `AnyVal`s.
    val f = file.value
    val l = line.value
    // The suspended function will have two
    // pointers for `f` and `l`. It could be a
    // single pointer if sourcecode had an
    // implicit for `fileName:line`.
    IOs(if (logger.isTraceEnabled) logger.trace(s"[$f:$l] $msg"))
  }

  /*inline*/
  def trace( /*inline*/ msg: => String, /*inline*/ t: => Throwable)(
      implicit
      file: sourcecode.FileName,
      line: sourcecode.Line
  ): Unit < IOs = {
    val f = file.value
    val l = line.value
    IOs(if (logger.isTraceEnabled) logger.trace(s"[$f:$l] $msg", t))
  }

  /*inline*/
  def debug( /*inline*/ msg: => String)(
      implicit
      file: sourcecode.FileName,
      line: sourcecode.Line
  ): Unit < IOs = {
    val f = file.value
    val l = line.value
    IOs(if (logger.isDebugEnabled) logger.debug(s"[$f:$l] $msg"))
  }

  /*inline*/
  def debug( /*inline*/ msg: => String, /*inline*/ t: => Throwable)(
      implicit
      file: sourcecode.FileName,
      line: sourcecode.Line
  ): Unit < IOs = {
    val f = file.value
    val l = line.value
    IOs(if (logger.isDebugEnabled) logger.debug(s"[$f:$l] $msg", t))
  }

  /*inline*/
  def info( /*inline*/ msg: => String)(
      implicit
      file: sourcecode.FileName,
      line: sourcecode.Line
  ): Unit < IOs = {
    val f = file.value
    val l = line.value
    IOs(if (logger.isInfoEnabled) logger.info(s"[$f:$l] $msg"))
  }

  /*inline*/
  def info( /*inline*/ msg: => String, /*inline*/ t: => Throwable)(
      implicit
      file: sourcecode.FileName,
      line: sourcecode.Line
  ): Unit < IOs = {
    val f = file.value
    val l = line.value
    IOs(if (logger.isInfoEnabled) logger.info(s"[$f:$l] $msg", t))
  }

  /*inline*/
  def warn( /*inline*/ msg: => String)(
      implicit
      file: sourcecode.FileName,
      line: sourcecode.Line
  ): Unit < IOs = {
    val f = file.value
    val l = line.value
    IOs(if (logger.isWarnEnabled) logger.warn(s"[$f:$l] $msg"))
  }

  /*inline*/
  def warn( /*inline*/ msg: => String, /*inline*/ t: => Throwable)(
      implicit
      file: sourcecode.FileName,
      line: sourcecode.Line
  ): Unit < IOs = {
    val f = file.value
    val l = line.value
    IOs(if (logger.isWarnEnabled) logger.warn(s"[$f:$l] $msg", t))
  }

  /*inline*/
  def error( /*inline*/ msg: => String)(
      implicit
      file: sourcecode.FileName,
      line: sourcecode.Line
  ): Unit < IOs = {
    val f = file.value
    val l = line.value
    IOs(if (logger.isErrorEnabled) logger.error(s"[$f:$l] $msg"))
  }

  /*inline*/
  def error( /*inline*/ msg: => String, /*inline*/ t: => Throwable)(
      implicit
      file: sourcecode.FileName,
      line: sourcecode.Line
  ): Unit < IOs = {
    val f = file.value
    val l = line.value
    IOs(if (logger.isErrorEnabled) logger.error(s"[$f:$l] $msg", t))
  }

  object unsafe {
    /*inline*/
    def trace( /*inline*/ msg: => String)(
        implicit
        file: sourcecode.FileName,
        line: sourcecode.Line
    ): Unit = {
      val f = file.value
      val l = line.value
      if (logger.isTraceEnabled) logger.trace(s"[$f:$l] $msg")
    }

    /*inline*/
    def trace( /*inline*/ msg: => String, /*inline*/ t: => Throwable)(
        implicit
        file: sourcecode.FileName,
        line: sourcecode.Line
    ): Unit = {
      val f = file.value
      val l = line.value
      if (logger.isTraceEnabled) logger.trace(s"[$f:$l] $msg", t)
    }

    /*inline*/
    def debug( /*inline*/ msg: => String)(
        implicit
        file: sourcecode.FileName,
        line: sourcecode.Line
    ): Unit = {
      val f = file.value
      val l = line.value
      if (logger.isDebugEnabled) logger.debug(s"[$f:$l] $msg")
    }

    /*inline*/
    def debug( /*inline*/ msg: => String, /*inline*/ t: => Throwable)(
        implicit
        file: sourcecode.FileName,
        line: sourcecode.Line
    ): Unit < IOs = {
      val f = file.value
      val l = line.value
      if (logger.isDebugEnabled) logger.debug(s"[$f:$l] $msg", t)
    }

    /*inline*/
    def info( /*inline*/ msg: => String)(
        implicit
        file: sourcecode.FileName,
        line: sourcecode.Line
    ): Unit = {
      val f = file.value
      val l = line.value
      if (logger.isInfoEnabled) logger.info(s"[$f:$l] $msg")
    }

    /*inline*/
    def info( /*inline*/ msg: => String, /*inline*/ t: => Throwable)(
        implicit
        file: sourcecode.FileName,
        line: sourcecode.Line
    ): Unit = {
      val f = file.value
      val l = line.value
      if (logger.isInfoEnabled) logger.info(s"[$f:$l] $msg", t)
    }

    /*inline*/
    def warn( /*inline*/ msg: => String)(
        implicit
        file: sourcecode.FileName,
        line: sourcecode.Line
    ): Unit = {
      val f = file.value
      val l = line.value
      if (logger.isWarnEnabled) logger.warn(s"[$f:$l] $msg")
    }

    /*inline*/
    def warn( /*inline*/ msg: => String, /*inline*/ t: => Throwable)(
        implicit
        file: sourcecode.FileName,
        line: sourcecode.Line
    ): Unit < IOs = {
      val f = file.value
      val l = line.value
      if (logger.isWarnEnabled) logger.warn(s"[$f:$l] $msg", t)
    }

    /*inline*/
    def error( /*inline*/ msg: => String)(
        implicit
        file: sourcecode.FileName,
        line: sourcecode.Line
    ): Unit = {
      val f = file.value
      val l = line.value
      if (logger.isErrorEnabled) logger.error(s"[$f:$l] $msg")
    }

    /*inline*/
    def error( /*inline*/ msg: => String, /*inline*/ t: => Throwable)(
        implicit
        file: sourcecode.FileName,
        line: sourcecode.Line
    ): Unit = {
      val f = file.value
      val l = line.value
      if (logger.isErrorEnabled) logger.error(s"[$f:$l] $msg", t)
    }
  }
}
