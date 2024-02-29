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

import scala.collection.mutable.ListBuffer
import scala.language.implicitConversions

// unit definitions
import coulomb.units.mks.{Meter, Second}

/**
 * ODE defines a system of differential equations by providing access to the rate equations.
 *
 * @author       Wolfgang Christian
 */

//case class State(
//                  var x: Array[Quantity[Double, Meter]], // x0, x1, x2
//                  var v: Array[Quantity[Double, Meter / Second]], // v1, v2, v3
//                  var t: Quantity[Double, Second]
//                )
//
//case class Rate(
//                 var v: Array[Quantity[Double, Meter / Second]],
//                 var a: Array[Quantity[Double, Meter / (Second ^ 2)]],
//                 var t: Quantity[Double, Second]
//               )

class State(numEqn: Integer) {
  var x = new Array[Quantity[Double, Meter]](numEqn) // x0, x1, x2
  var v =  new Array[Quantity[Double, Meter / Second]](numEqn) // v0, v1, v2
  var t = 0.0.withUnit[Second]
}

class Rate(numEqn: Integer) {
  var v = new Array[Quantity[Double, Meter / Second]](numEqn)
  var a = new  Array[Quantity[Double, Meter / (Second ^ 2)]](numEqn) // a0, a1, a2
  var t = 0.0.withUnit[Second]
}

trait ODE {
  /**
   * Gets the state variables.
   *
   * The getState method is invoked by an ODESolver to obtain the initial state of the system.
   * The ODE solver advances the solution and then copies new values into the
   * state array at the end of the solution step.
   *
   * @return state  the state
   */
  def getState: State

  /**
   * Gets the rate of change using the argument's state variables.
   *
   * This method may be invoked many times with different intermediate states
   * as an ODESolver is carrying out the solution.
   *
   * @param state the state array
   * @param rate  the rate array
   */
  def getRate(state: State, rate: Rate): Unit
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