/*
 * Scala (https://www.scala-lang.org)
 *
 * Copyright EPFL and Lightbend, Inc.
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

package scala
package runtime

/** This trait is used as a basis for implementation of all partial function literals. */
abstract class AbstractPartialFunction[@specialized(Specializable.Arg) -T1, @specialized(Specializable.Return) +R]
    extends PartialFunction[T1, R] {
  // this method must be overridden for better performance,
  // override def applyOrElse[A1 <: T1, B1 >: R](x: A1, default: A1 => B1): B1 = ???
}
