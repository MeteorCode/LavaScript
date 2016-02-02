package com.meteorcode.lavascript

import org.dynjs.runtime.DynJS
import org.dynjs.jsr223.{DynJSScriptEngineFactory, DynJSScriptEngine}

import javax.script.{ScriptEngine, ScriptEngineFactory, Bindings, ScriptContext}

import scala.collection.Map
import scala.collection.JavaConverters.{ mapAsJavaMapConverter
                                       , mapAsScalaMapConverter }
import scala.language.postfixOps
import scala.util.Try


/**
  * @constructor
  * Created by hawk on 2/1/16.
  */
class State( bindings: Map[String, Object]
           , factory: ScriptEngineFactory = new DynJSScriptEngineFactory )
extends StateLike {

  override type Self = State
  private[this] val e: Executor = new Executor(factory.getScriptEngine)

  def this() = this(Map())

  override def +[B1 >: AnyRef](kv: (String, B1)): Map[String, B1]
    = { e.bindings()
         .put(kv._1, kv._2.asInstanceOf[Object])
        this
      }

  override def get(key: String): Option[AnyRef]
    = Option(e.bindings() get key)

  override def iterator: Iterator[(String, AnyRef)]
    = e.bindings().asScala.iterator

  override def -(key: String): Map[String, AnyRef]
  = { e.bindings() remove key
      this
    }

  override def bind(script: String): Try[Self]
    = Try (e eval script ) map (_ => this)

  def invoke(function: String, args: AnyRef*): Try[Self]
    = ??? // it would be nice to wrap the DynJs invoke method function...

  /** Wrapper class for a State's JS execution engine.
    *
    * The execution engine is defined as a private inner class so that we can
    * swear it isn't modified externally. This saves us from having to
    * re-inject bindings every time, as we can prove nobody else has touched
    * the JavaScript universe.
    */
  case class Executor(engine: ScriptEngine) {

    @inline def bindings(): java.util.Map[String, Object]
      = engine.getBindings(ScriptContext.GLOBAL_SCOPE)

    @inline def eval(script: String): Try[Object]
      = Try(engine eval(script, engine.getBindings(ScriptContext.GLOBAL_SCOPE)))

  }
}
