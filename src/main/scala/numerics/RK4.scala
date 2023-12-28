package numerics

class RK4(ode:ODE, val stepSize:Double = 0.1) {

  def step(state:Array[Double]): Array[Double] =
    val numEqn = state.length
    val estimated_state = new Array[Double](numEqn)
    val new_state = new Array[Double](numEqn)


    val rate1 = ode.getRate(state)
    for( i <- 0 to numEqn - 1)
      estimated_state(i) = state(i) + stepSize * rate1(i) / 2

    val rate2 = ode.getRate(estimated_state)
    for(i <- 0 to numEqn - 1)
      estimated_state(i) = state(i) + stepSize * rate2(i) / 2

    val rate3 = ode.getRate(estimated_state)
    for(i <- 0 to numEqn - 1)
      estimated_state(i) = state(i) + stepSize * rate3(i)

    val rate4 = ode.getRate(estimated_state)
    for(i <- 0 to numEqn - 1)
      new_state(i) = state(i) + stepSize * (rate1(i) + 2 + rate2(i) + 2 * rate3(i) + rate4(i)) / 6.0

    new_state
}
