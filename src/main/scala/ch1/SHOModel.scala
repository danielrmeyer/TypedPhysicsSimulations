/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

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

/*
 * Open Source Physics software is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public License (GPL) as
 * published by the Free Software Foundation; either version 2 of the License,
 * or(at your option) any later version.
 *
 * Code that uses any portion of the code in the org.opensourcephysics package
 * or any subpackage (subdirectory) of this package must also be released
 * under the GNU GPL license.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307 USA
 * or view the license online at http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2007  The Open Source Physics project
 *                     http://www.opensourcephysics.org
 *
 * Modifications made:
 * [Translated the code to Scala]
 * Copyright (c) [2024] [Daniel Reagan Meyer]
 */