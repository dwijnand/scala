// scalac: -Werror -Xlint:strict-unsealed-patmat

class Foo {
  def test(i: Int) = i match {
    case value if value < 0  => true
    case value if value > 10 => false
  }
}
