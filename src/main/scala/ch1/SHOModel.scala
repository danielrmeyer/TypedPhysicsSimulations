/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

package ch01

import coulomb.*
import coulomb.syntax.*
import coulomb.units.constants.{Kilogram, Radian}
import coulomb.units.mks.Newton
import numerics.{ODE, ODESolver, RK4}
import org.opensourcephysics.controls.*
import org.opensourcephysics.display.Circle
import org.opensourcephysics.frames.*
// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.all.{*, given}
// unit and value type policies for operations
import coulomb.policy.standard.given

import scala.collection.mutable.ListBuffer
import scala.language.implicitConversions

// unit definitions
import coulomb.units.mks.{Meter, Second}

import numerics.{State, Rate}

class SHOModel extends Circle with ODE {
  // initial state values = {x, v, t}

  var state = new State(1)
  state.x = Array(0.0.withUnit[Meter])
  state.v = Array(0.0.withUnit[Meter / Second])
  state.t = 0.0.withUnit[Second]

  private val k = 1.0.withUnit[Newton / Meter] // spring constant
  private val b = 0.2.withUnit[(Newton * Second) / Meter] // damping constant
  private val odeSolver: ODESolver = new RK4(this)

  /**
   * Initializes the position, velocity, and time.
   *
   * @param x position
   * @param v velocity
   * @param t time
   */
  def initialize(
                  x: Quantity[Double, Meter],
                  v: Quantity[Double, Meter / Second],
                  t: Quantity[Double, Second]): Unit =
    this.x = x.value // Set the Circle x value

    state.x = Array(x)
    state.v = Array(v)
    state.t = t

  /**
   * Gets the time.
   *
   * @return time
   */
  def getTime: Quantity[Double, Second] = state.t

  /**
   * Gets the state array.
   *
   * @return an array containing {x, v, t}
   */
  override def getState: State = state

  /**
   * Calculates the rate array using the given state.
   * Values in the rate array are overwritten.
   *
   * @param state the state
   * @param rate  the rate
   */
  override def getRate(state: State, rate: Rate): Unit = {
    val force = -k * state.x(0) - b * state.v(0)
    rate.v(0) = state.v(0) // dx/dt = v
    rate.a(0) = force / 1.0.withUnit[Kilogram] // dv/dt = force,  assuming unit mass
    rate.t = 1.0.withUnit[Second] // dt/dt = 1
  }

  /**
   * Moves the particle.
   */
  def move(): Unit = {
    odeSolver.step()
    setX(state.x(0).value)
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