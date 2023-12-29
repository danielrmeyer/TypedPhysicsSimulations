package numerics

import coulomb.*
import coulomb.syntax.*

// algebraic definitions
import algebra.instances.all.given

// unit and value type policies for operations
import coulomb.policy.standard.given

import scala.language.implicitConversions

// unit definitions
import coulomb.units.mks.{Kilogram, Meter, Newton, Second}

case class State(x: Quantity[Double, Meter], v: Quantity[Double, Meter / Second], t: Quantity[Double, Second])

trait ODE {
  
  
  def getState(): State
  
  
  def getRate(state:State): State
  
}