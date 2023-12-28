package numerics

abstract class AbstractODESolver(ode:ODE) extends ODESolver {
  val stepSize:Double = 0.1
  val numEqn:Integer = 0
  
  initialize(0.1)

}
