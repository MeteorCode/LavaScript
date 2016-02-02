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
extends StateLike[State] {

  private[this] val e: Executor = Executor(factory.getScriptEngine)

  e.bindings() putAll bindings.asJava

  override def +=(kv: (String, Object))
    = { e.bindings()
         .put(kv._1, kv._2.asInstanceOf[Object])
        this
      }

  override def -=(key: String)
    = { e.bindings() remove key
        this
      }


  override def get(key: String): Option[Object]
    = Option(e.bindings() get key)

  override def iterator: Iterator[(String, Object)]
    = e.bindings().asScala.iterator


  override def bind(script: String): Try[State]
    = Try (e eval script ) map (_ => this)

  def invoke(function: String, args: AnyRef*): Try[State]
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
