package org.duangsuse.geekapk.helpers

inline infix fun <reified A : Number, B> ((A) -> B).loopFor(times: Int) {
  assert(times >= 0) { "Input $times must be greater or equal to zero" }
  var restIteration = times
  while (restIteration > 0) {
    this(restIteration as A)
    restIteration--
  }
}

inline fun <reified R : Number> Number.times(): R = this as R
