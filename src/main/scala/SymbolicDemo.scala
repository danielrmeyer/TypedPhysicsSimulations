import smile.cas.{Var, cot, pimpDouble}

@main
def symbolicDemoApp() =
  println("Let do math")

  val x = Var("x")
  val y = Var("y")
  val e = x ** 2 + y ** 3 + x ** 2 * cot(y ** 3)
  val dx = e.d(x)
  
  println(dx)