package numerics

// fundamental coulomb types and methods
import coulomb.*
import coulomb.syntax.*

// algebraic definitions
import algebra.instances.all.given

// unit and value type policies for operations
import coulomb.policy.standard.given

import scala.language.implicitConversions

// unit definitions
import coulomb.units.mks.{Meter, Newton, Second, Kilogram}


class RK4(ode:ODE, val stepSize:Double = 0.1) {

  def step(state:State): State =
    val numEqn = 3

//    val estimated_state = new Array[Double](numEqn)
//    val new_state = new Array[Double](numEqn)


    val rate1 = ode.getRate(state)
    var estimated_state = State(
      state.x + rate1.x * stepSize / 2,
      state.v + rate1.v * stepSize / 2,
      state.t + rate1.t * stepSize / 2
    )

//    for( i <- 0 to numEqn - 1)
//      estimated_state(i) = state(i) + stepSize * rate1(i) / 2

    val rate2 = ode.getRate(estimated_state)
    estimated_state = State(
      state.x + rate2.x * stepSize / 2,
      state.v + rate2.v * stepSize / 2,
      state.t + rate2.t * stepSize / 2
    )

//    for(i <- 0 to numEqn - 1)
//      estimated_state(i) = state(i) + stepSize * rate2(i) / 2

    val rate3 = ode.getRate(estimated_state)
    estimated_state = State(
      state.x + rate3.x * stepSize,
      state.v + rate3.v * stepSize ,
      state.t + rate3.t * stepSize
    )

//    for(i <- 0 to numEqn - 1)
//      estimated_state(i) = state(i) + stepSize * rate3(i)

    val rate4 = ode.getRate(estimated_state)
    val new_state = State(
      state.x + (rate1.x + 2.withUnit[Meter] + rate2.x + rate3.x * 2 + rate4.x) * stepSize / 6.0,
      state.v + (rate1.v + 2.withUnit[Meter / Second] + rate2.v + rate3.v * 2 + rate4.v) * stepSize / 6.0,
      state.t + (rate1.t + 2.withUnit[Second] + rate2.t + rate3.t * 2 + rate4.t) * stepSize / 6.0
    )

//    for(i <- 0 to numEqn - 1)
//      new_state(i) = state(i) + stepSize * (rate1(i) + 2 + rate2(i) + 2 * rate3(i) + rate4(i)) / 6.0

    new_state
}
