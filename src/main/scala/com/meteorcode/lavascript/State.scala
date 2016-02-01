package com.meteorcode.lavascript

import org.dynjs.runtime.Runner

import scala.collection.Map

/**
  * Created by hawk on 2/1/16.
  */
class State(private[this] val runner: Runner)
extends StateLike {

  override type Self = State

  override def +[B1 >: AnyRef](kv: (String, B1)): Map[String, B1]
    = ???

  override def get(key: String): Option[AnyRef]
    = ???

  override def iterator: Iterator[(String, AnyRef)]
    = ???

  override def -(key: String): Map[String, AnyRef]
    = ???

  override def bind(script: String)
    = ???
}
