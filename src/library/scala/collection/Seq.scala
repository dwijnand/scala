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
package collection

import generic._
import mutable.Builder

/** A base trait for sequences.
 *  $seqInfo
 */
trait Seq[+A] extends PartialFunction[Int, A]
                      with Iterable[A]
                      with GenSeq[A]
                      with GenericTraversableTemplate[A, Seq]
                      with SeqLike[A, Seq[A]] {
  override def companion: GenericCompanion[Seq] = Seq

  override def seq: Seq[A] = this
}

/** $factoryInfo
 *  The current default implementation of a $Coll is a `List`.
 *  @define coll sequence
 *  @define Coll `Seq`
 */
object Seq extends SeqFactory[Seq] {
  override def buildFromOrNull[A](source: TraversableOnce[A]): Seq[A] = source match {
    case c: immutable.Seq[A] => c
    case _ => null
  }

  /** $genericCanBuildFromInfo */
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, Seq[A]] = ReusableCBF.asInstanceOf[GenericCanBuildFrom[A]]

  def newBuilder[A]: Builder[A, Seq[A]] = immutable.Seq.newBuilder[A]
}

/** Explicit instantiation of the `Seq` trait to reduce class file size in subclasses. */
abstract class AbstractSeq[+A] extends AbstractIterable[A] with Seq[A]
