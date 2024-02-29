/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <https://www.compadre.org/osp/>
 */

package numerics

import algebra.instances.all.given
import coulomb.ops.algebra.all.{*, given}
// unit and value type policies for operations
import coulomb.policy.standard.given
import coulomb.*
import coulomb.syntax.*
import coulomb.units.constants.{Kilogram, Radian}
import coulomb.units.mks.Newton
import coulomb.ops.algebra.all.{*, given}
import coulomb.units.mks.{Meter, Second}
/**
 * AbstractODE provides a common superclass for ODESolvers.
 *
 * @author       Wolfgang Christian
 * @version 1.0
 */
abstract class AbstractODESolver(val ode: ODE) extends ODESolver {
  protected var stepSize = 0.1.withUnit[Second] // parameter increment such as delta time
  protected var numEqn: Int = 0        // number of equations
  //var ode: ODE               // object that computes rate

  /**
   * Constructs the ODESolver for a system of ordinary differential equations.
   *
   * @param _ode the system of differential equations.
   */
  def this() = {
    this(null)
    initialize(0.1.withUnit[Second])
  }

  /**
   * Steps (advances) the differential equations by the stepSize.
   *
   * The ODESolver invokes the ODE's getRate method to obtain the initial state of the system.
   * The ODESolver then advances the solution and copies the new state into the
   * state array at the end of the solution step.
   *
   * @return the step size
   */
  def step(): Quantity[Double, Second]

  /**
   * Sets the step size.
   *
   * The step size remains fixed in this algorithm
   *
   * @param _stepSize
   */
  def setStepSize(_stepSize: Quantity[Double, Second]): Unit = {
    stepSize = _stepSize
  }

  /**
   * Initializes the ODE solver.
   *
   * The rate array is allocated.  The number of differential equations is
   * determined by invoking getState().length on the ODE.
   *
   * @param _stepSize
   */
  def initialize(_stepSize: Quantity[Double, Second]): Unit = {
    stepSize = _stepSize
    val state = ode.getState
    if (state == null) { // state vector not defined
      numEqn = 0
    } else {
      numEqn = state.x.length
    }
  }

  /**
   * Gets the step size.
   *
   * The stepsize is constant in this algorithm
   *
   * @return the step size
   */
  def getStepSize(): Quantity[Double, Second] = {
    stepSize
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