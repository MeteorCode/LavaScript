package com.meteorcode.lavascript

import org.dynjs.runtime.DynJS
import org.dynjs.jsr223.DynJSScriptEngine

import javax.script.Bindings
import javax.script.ScriptContext

import scala.collection.Map
import scala.collection.JavaConverters.{ mapAsJavaMapConverter
                                       , mapAsScalaMapConverter }
import scala.language.postfixOps
import scala.util.Try


/**
  * @constructor
  * Created by hawk on 2/1/16.
  */
class State(private[this] var _bindings: Map[String, Object])
extends StateLike {

  override type Self = State

  def this() = this(Map())

  override type Self = State

  override def +[B1 >: AnyRef](kv: (String, B1)): Map[String, B1]
    = { _bindings += kv; this }

  override def get(key: String): Option[AnyRef]
    = _bindings get key

  override def iterator: Iterator[(String, AnyRef)]
    = _bindings iterator

  override def -(key: String): Map[String, AnyRef]
  = { _bindings -= key; this }

  override def bind(script: String): Try[Self]
    = {
      // TODO: is it faster to see if the engine matches ours?
//      engine.getBindings(ScriptContext.ENGINE_SCOPE)
//            .putAll(_bindings.asJava)
//
//      Try(engine eval script) map { _ =>
//        new State(engine.getBindings(ScriptContext.ENGINE_SCOPE)
//                        .asScala)

      }
    }
}
