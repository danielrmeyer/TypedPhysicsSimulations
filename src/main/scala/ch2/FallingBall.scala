/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

package ch02

import coulomb.*
import coulomb.syntax.*
import coulomb.units.constants.Radian
// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.all.{*, given}
// unit and value type policies for operations
import coulomb.policy.standard.given
import scala.language.implicitConversions
// unit definitions
import coulomb.units.mks.{Meter, Second}


class FallingBall {
  var y: Quantity[Double, Meter] = _ // Instance variables
  var v: Quantity[Double, Meter / Second] = _
  var t: Quantity[Double, Second] = _
  var dt: Quantity[Double, Second] = _
  val g  = 9.8.withUnit[Meter / (Second ^ 2)] // Gravitational constant

  // Constructor
  println("A new FallingBall object is created.")

  /**
   * Steps (advances) the position of the ball using the Euler algorithm.
   */
  def step(): Unit = {
    y += v * dt // Euler algorithm for numerical solution
    v -= g * dt
    t += dt
  }

  /**
   * Computes the position of the ball using the analytic solution of the equation of motion.
   *
   * @param y0 double
   * @param v0 double
   * @return double
   */
  def analyticPosition(y0: Quantity[Double, Meter], v0: Quantity[Double, Meter / Second]): Quantity[Double, Meter] =
    y0 + v0 * t - 0.5 * g * t * t

  /**
   * Computes the velocity of the ball using the analytic solution of the equation of motion.
   *
   * @param v0 double
   * @return double
   */
  def analyticVelocity(v0: Quantity[Double, Meter / Second]): Quantity[Double, Meter / Second] =
    v0 - g * t
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
 * [Added Unit Awareness] 
 * Copyright (c) [2024] [Daniel Reagan Meyer]
 */