package ch01

import org.opensourcephysics.display.Circle
import numerics.{ODE, ODESolver, RK4}

class SHOModel extends Circle with ODE {
  // initial state values = {x, v, t}
  private val state: Array[Double] = Array(0.0, 0.0, 0.0)
  private val k: Double = 1       // spring constant
  private val b: Double = 0.2     // damping constant
  private val odeSolver: ODESolver = new RK4(this)

  /**
   * Initializes the position, velocity, and time.
   *
   * @param x position
   * @param v velocity
   * @param t time
   */
  def initialize(x: Double, v: Double, t: Double): Unit = {
    this.x = x
    state(0) = x
    state(1) = v
    state(2) = t
  }

  /**
   * Gets the time.
   *
   * @return time
   */
  def getTime: Double = state(2)

  /**
   * Gets the state array.
   *
   * @return an array containing {x, v, t}
   */
  override def getState: Array[Double] = state

  /**
   * Calculates the rate array using the given state.
   * Values in the rate array are overwritten.
   *
   * @param state the state
   * @param rate  the rate
   */
  override def getRate(state: Array[Double], rate: Array[Double]): Unit = {
    val force: Double = -k * state(0) - b * state(1)
    rate(0) = state(1) // dx/dt = v
    rate(1) = force    // dv/dt = force
    rate(2) = 1        // dt/dt = 1
  }

  /**
   * Moves the particle.
   */
  def move(): Unit = {
    odeSolver.step()
    setX(state(0))
  }
}
