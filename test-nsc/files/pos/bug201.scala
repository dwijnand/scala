class C[a] { def f: a = f; }
class D[b] { class E extends C[b]; }
object Test {
  val d = new D[int];
  def e = new d.E;
  e.f;
}
