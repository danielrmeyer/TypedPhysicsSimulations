/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <https://www.compadre.org/osp/>
 */

package numerics

/**
 * Title:        RK4
 * Description:  A fourth order Runge-Kutta ODE solver.
 * @author       Wolfgang Christian
 * @version 1.0
 */

import algebra.instances.all.given
import coulomb.ops.algebra.all.{*, given}
// unit and value type policies for operations
import coulomb.policy.standard.given
import coulomb.*
import coulomb.syntax.*
import coulomb.units.constants.{Kilogram, Radian}
import coulomb.units.mks.Newton

import scala.collection.mutable.ListBuffer
import scala.language.implicitConversions

// unit definitions
import coulomb.units.mks.{Meter, Second}


class RK4(override val ode: ODE) extends AbstractODESolver(ode) {
  //(Array(0.0.withUnit[Meter / Second]), Array(0.0.withUnit[Meter / Second]), 0.0.withUnit[Second]
  private var rate1:Rate = _
  private var rate2:Rate = _
  private var rate3:Rate = _
  private var rate4:Rate = _
  private var estimated_state:State = _

  /**
   * Constructs the RK4 ODESolver for a system of ordinary  differential equations.
   *
   * @param ode the system of differential equations.
   */
  def this() = this(null) // TODO get rid of this null

  /**
   * Initializes the ODE solver and allocates the rate and state arrays.
   * The number of differential equations is determined by invoking getState().length on the superclass.
   *
   * @param stepSize
   */
  override def initialize(stepSize: Quantity[Double, Second]): Unit =
    super.initialize(stepSize)
    rate1 = new Rate(numEqn)
    rate2 = new Rate(numEqn)
    rate3 = new Rate(numEqn)
    rate4 = new Rate(numEqn)
    estimated_state = new State(numEqn)

  /**
   * Steps (advances) the differential equations by the stepSize.
   *
   * The ODESolver invokes the ODE's getRate method to compute the rate at various intermediate states.
   *
   * The ODESolver then advances the solution and copies the new state into the
   * ODE's state array at the end of the solution step.
   *
   * @return the step size
   */
  override def step(): Quantity[Double, Second] =
    val state = ode.getState
    println(state.x(0))
    if (state == null)
      stepSize
    if (state.x.length!= numEqn)
      initialize(stepSize)
    ode.getRate(state, rate1)
    for (i <- 0 until numEqn)
      estimated_state.x(i) = state.x(i) + stepSize * rate1.v(i) / 2
    ode.getRate(estimated_state, rate2)
    for (i <- 0 until numEqn)
      estimated_state.x(i) = state.x(i) + stepSize * rate2.v(i) / 2
    ode.getRate(estimated_state, rate3)
    for (i <- 0 until numEqn)
      estimated_state.x(i) = state.x(i) + stepSize * rate3.v(i)
    ode.getRate(estimated_state, rate4)
    for (i <- 0 until numEqn)
      state.x(i) = state.x(i) + stepSize * (rate1.v(i) + 2 * rate2.v(i) + 2 * rate3.v(i) + rate4.v(i)) / 6.0
    stepSize

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
