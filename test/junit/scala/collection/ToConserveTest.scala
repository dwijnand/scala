package scala.collection

import org.junit.Assert.{assertNotSame, assertSame}
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import scala.collection.{immutable => i, mutable => m}
import scala.language.implicitConversions
import scala.{collection => c}

@RunWith(classOf[JUnit4])
class ToConserveTest {
  @Test def toConserveList: Unit = {
    val l: c.Traversable[Int] = (1 to 3).toList

    assertSame(l, l.toList)
    assertSame(l, l.toSeq)
    assertSame(l, l.toIterable)
    assertSame(l, l.toTraversable)

    // assertSame(l, l.to[c.Traversable])            // Predef.fallbackStringCanBuildFrom -- creates IndexedSeq
    assertSame(l, l.to(c.Traversable.canBuildFrom))
    // assertSame(l, l.to[i.Traversable])            // fallbackStringCanBuildFrom
    assertSame(l, l.to(i.Traversable.canBuildFrom))

    // assertSame(l, l.to[c.Iterable])               // fallbackStringCanBuildFrom
    assertSame(l, l.to(c.Iterable.canBuildFrom))
    // assertSame(l, l.to[i.Iterable])               // fallbackStringCanBuildFrom
    assertSame(l, l.to(i.Iterable.canBuildFrom))

    // assertSame(l, l.to[c.Seq])                    // fallbackStringCanBuildFrom
    assertSame(l, l.to(c.Seq.canBuildFrom))
    // assertSame(l, l.to[i.Seq])                    // fallbackStringCanBuildFrom
    assertSame(l, l.to(i.Seq.canBuildFrom))

    assertSame(l, l.to[c.LinearSeq])
    assertSame(l, l.to[i.LinearSeq])

    assertSame(l, l.to[List])
  }

  @Test def toConserveImmutableHashSet: Unit = {
    val s: c.Iterable[Int] = (1 to 10).to[i.HashSet]
    assertSame(s, s.toSet)
    assertSame(s, s.toIterable)

    // assertSame(s, s.to[c.Iterable]) // fallbackStringCanBuildFrom
    assertSame(s, s.to(c.Iterable.canBuildFrom))
    // assertSame(s, s.to[i.Iterable]) // fallbackStringCanBuildFrom
    assertSame(s, s.to(i.Iterable.canBuildFrom))

    assertSame(s, s.to[c.Set])     // works -- thanks to SetBuilderImpl?
    assertSame(s, s.to[i.Set])     // works -- thanks to SetBuilderImpl?

    assertSame(s, s.to[i.HashSet]) // works -- thanks to HashSetBuilder?
  }

  @Test def toConserveImmutableHashMap: Unit = {
    val m: c.Iterable[(Int, Int)] = i.HashMap((1 to 10).map(x => (x, x)): _*)

    assertSame(m, m.toMap)
    assertSame(m, m.toIterable)

    // assertSame(m, m.to[c.Iterable]) // fallbackStringCanBuildFrom
    assertSame(m, m.to(c.Iterable.canBuildFrom))
    // assertSame(m, m.to[i.Iterable]) // fallbackStringCanBuildFrom
    assertSame(m, m.to(i.Iterable.canBuildFrom))

    // to takes a single parameter type constructor, so `to[Map]` doesn't work
    // assertSame(m, m.to[c.Map])
    // assertSame(m, m.to[i.Map])
    // assertSame(m, m.to[i.HashMap])

    // assertSame(m, m.to(c.Map.canBuildFrom))  // TODO
    assertSame(m, m.to(i.Map.canBuildFrom))     // works -- thanks to MapBuilderImpl?
    assertSame(m, m.to(i.HashMap.canBuildFrom)) // works -- thanks to HashMapBuilder?
  }

  @Test def toConserveStream: Unit = {
    val l: c.Iterable[Int] = (1 to 10).to[Stream]

    assertSame(l, l.toStream)
    assertSame(l, l.toSeq)
    assertSame(l, l.toIterable)

    // assertSame(l, l.to[c.Iterable]) // fallbackStringCanBuildFrom
    assertSame(l, l.to(c.Iterable.canBuildFrom))
    // assertSame(l, l.to[i.Iterable]) // fallbackStringCanBuildFrom
    assertSame(l, l.to(i.Iterable.canBuildFrom))

    // assertSame(l, l.to[c.Seq]) // fallbackStringCanBuildFrom
    assertSame(l, l.to(c.Seq.canBuildFrom))
    // assertSame(l, l.to[i.Seq]) // fallbackStringCanBuildFrom
    assertSame(l, l.to(i.Seq.canBuildFrom))

    assertSame(l, l.to[c.LinearSeq])
    assertSame(l, l.to[i.LinearSeq])

    // assertSame(l, l.to[Stream]) // TODO
 }

  @Test def toRebuildMutable: Unit = {
    val s: c.Iterable[Int] = (1 to 3).to[m.HashSet]
    assertSame(s, s.toIterable)
    assertSame(s, s.toTraversable)
    assertNotSame(s, s.to[c.Iterable])
    assertNotSame(s, s.to[m.Iterable])
    assertNotSame(s, s.to[c.Set])
    assertNotSame(s, s.to[m.Set])
    assertNotSame(s, s.to[m.HashSet])

    val b: c.Iterable[Int] = (1 to 6).to[m.ArrayBuffer]
    assertSame(b, b.toIterable)
    assertSame(b, b.toTraversable)
    assertNotSame(b, b.toBuffer)
    assertNotSame(b, b.to[c.Iterable])
    assertNotSame(b, b.to[m.Iterable])
    assertNotSame(b, b.to[c.Seq])
    assertNotSame(b, b.to[m.Seq])
    assertNotSame(b, b.to[m.Buffer])
    assertNotSame(b, b.to[m.ArrayBuffer])
  }
}
