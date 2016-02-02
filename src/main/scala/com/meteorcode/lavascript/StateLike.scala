package com.meteorcode.lavascript


import scala.util.Try
import javax.script.ScriptEngine
/**
  * Created by hawk on 2/1/16.
  */
trait StateLike
extends Map[String, AnyRef] {

  type Self <: StateLike

  // TODO: should this return a `Try`?
  // TODO: possibly this should take an org.dynjs.runtime.JSProgram 
  //       rather than a string??
  def bind(script: String): Try[Self]

  /**
    * Haskell's `>>=` operator just for fun.
 *
    * @param script
    * @return
    */
  @inline def >>= (script: String): Try[Self]
    = bind(script)

  /**
    * The hash of the LavaScript execution state.
    *
    * Note that this is _not_ the same as a given `StateLike` object's
    * `hashCode`.
    *
    * @return the hash of the LavaScript execution state
    */
  def stateHash: Int
    = this.keys.foldLeft(0) { (hash, key) => hash + key.hashCode }
    + this.foldLeft(0) { (hash, value) => hash + value.hashCode }

}
