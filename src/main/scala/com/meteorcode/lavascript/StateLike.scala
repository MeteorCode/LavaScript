package com.meteorcode.lavascript

import org.dynjs.runtime.Runner

/**
  * Created by hawk on 2/1/16.
  */
trait StateLike {
  type Self <: StateLike

  def bind(script: String): Self

  def >>= (script: String): Self
    = bind(script)
}
