package com.meteorcode.lavascript

import org.dynjs.runtime.Runner

/**
  * Created by hawk on 2/1/16.
  */
trait StateLike[A <: StateLike[A]]
extends Map[String, AnyRef] {

  type Self <: StateLike

  // TODO: should this return a `Try`?
  def bind(script: String): Self

  /**
    * Haskell's `>>=` operator just for fun.
    * @param script
    * @return
    */
  @inline def >>= (script: String): Self
    = bind(script)

  /**
    * The hash of the LavaScript execution state.
    *
    * Note that this is _not_ the same as a given `StateLike` object's
    * `hashCode`.
    * @return the hash of the LavaScript execution state
    */
  def stateHash: Int
    = ??? // TODO: this can be defined by iterating over the state values?



}
