import coulomb.*
import coulomb.syntax.*

// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.all.{*, given}

// unit and value type policies for operations
import coulomb.policy.standard.given
import scala.language.implicitConversions

// unit definitions
import coulomb.units.mks.{Meter, Second}


@main
def simulate() =
  val y0 = 10.0.withUnit[Meter]
  val v0 = 0.0.withUnit[Meter / Second]

  val dt = 0.01.withUnit[Second]
  val g = 9.8.withUnit[Meter / (Second ^ 2)]

  var y = y0
  var v = v0
  var t = 0.0.withUnit[Second]

  for(n <- 0 to 100)
    y = y + v*dt
    v = v - dt*g
    t = t + dt

  println("Results")
  println("final time = " + t)
  println("y = "+y+" v = " + v)
  
  val yAnalytic = y0 + v0 * t - 0.5 * g * t * t
  val vAnalytic = v0 - g * t
  System.out.println("analytic y = " + yAnalytic + " v = " + vAnalytic)


  println("Hello")
