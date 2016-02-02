package com.meteorcode.lavascript

import org.dynjs.runtime.Runner

import scala.collection.Map
import scala.language.postfixOps

/**
  * @constructor
  * Created by hawk on 2/1/16.
  */
class State(private[this] var _bindings: Map[String, Object])
extends StateLike {

  override type Self = State

  override def +[B1 >: AnyRef](kv: (String, B1)): Map[String, B1]
    = { _bindings += kv; this }

  override def get(key: String): Option[AnyRef]
    = _bindings get key

  override def iterator: Iterator[(String, AnyRef)]
    = _bindings iterator

  override def -(key: String): Map[String, AnyRef]
  = { _bindings -= key; this }

  override def bind(script: String, runner: Runner): Self
    = ???
}
