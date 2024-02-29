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
 * ODE defines a minimal differential equation solver.
 * @author       Wolfgang Christian
 */
trait ODESolver {
  /**
   * Initializes the ODE solver.
   *
   * ODE solvers use this method to allocate temporary arrays that may be required to carry out the solution.
   * The number of differential equations is determined by invoking getState().length on the ODE.
   *
   * @param stepSize
   */
  def initialize(stepSize: Quantity[Double, Second]): Unit

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
   * Sets the initial step size.
   *
   * The step size may change if the ODE solver implements an adaptive step size algorithm
   * such as RK4/5.
   *
   * @param stepSize
   */
  def setStepSize(stepSize: Quantity[Double, Second]): Unit

  /**
   * Gets the step size.
   *
   * @return the step size
   */
  def getStepSize(): Quantity[Double, Second]
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