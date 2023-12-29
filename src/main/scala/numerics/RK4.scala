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
import coulomb.units.mks.{Meter, Second}


class RK4(ode:ODE, val stepSize:Double = 0.1) {

  def step(state:State): State =

    val rate1 = ode.getRate(state)
    var estimated_state = State(
      state.x + rate1.x * stepSize / 2,
      state.v + rate1.v * stepSize / 2,
      state.t + rate1.t * stepSize / 2
    )

    val rate2 = ode.getRate(estimated_state)
    estimated_state = State(
      state.x + rate2.x * stepSize / 2,
      state.v + rate2.v * stepSize / 2,
      state.t + rate2.t * stepSize / 2
    )

    val rate3 = ode.getRate(estimated_state)
    estimated_state = State(
      state.x + rate3.x * stepSize,
      state.v + rate3.v * stepSize ,
      state.t + rate3.t * stepSize
    )

    val rate4 = ode.getRate(estimated_state)
    val new_state = State(
      state.x + (rate1.x + 2.withUnit[Meter] + rate2.x + rate3.x * 2 + rate4.x) * stepSize / 6.0,
      state.v + (rate1.v + 2.withUnit[Meter / Second] + rate2.v + rate3.v * 2 + rate4.v) * stepSize / 6.0,
      state.t + (rate1.t + 2.withUnit[Second] + rate2.t + rate3.t * 2 + rate4.t) * stepSize / 6.0
    )

    new_state
}
