package numerics

import org.opensourcephysics.controls.*
import org.opensourcephysics.frames.*
import coulomb.*
import coulomb.syntax.*
import coulomb.units.constants.Radian
// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.all.{*, given}
// unit and value type policies for operations
import coulomb.policy.standard.given
import scala.language.implicitConversions
// unit definitions
import coulomb.units.mks.{*, given}

object Math {
  def cos(theta: Quantity[Double, Radian]): Quantity[Double, Radian] =
    java.lang.Math.cos(theta.value).withUnit[Radian]
    
  def sin(theta: Quantity[Double, Radian]): Quantity[Double, Radian] =
    java.lang.Math.sin(theta.value).withUnit[Radian]

  //def sqrt(x: Quantity[Double, Pow[V, U, P]]): Double = java.lang.Math.sqrt(x.value)  // TODO what does the square root do to units?

  def sqrt[V](q: Quantity[Double, V ^ 2])(using ops: ValueOf[V]): Quantity[Double, V] = {
    scala.math.sqrt(q.value).withUnit[V]
  }
  
  val PI = java.lang.Math.PI.withUnit[Radian]
}