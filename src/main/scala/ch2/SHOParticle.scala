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
import coulomb.units.mks.{*, given}

import numerics.Math

class SHOParticle(initialY: Quantity[Double, Meter], initialV: Quantity[Double, Meter / Second]) extends Particle {
  import SHOParticle._

  println("A new SHO object is being created.")
  y = initialY
  v = initialV

  private var y0 = y
  private var v0 = v

  /**
   * Steps (advances) the dynamical variables using the Euler-Cromer algorithm.
   */
  override def step(): Unit = {
    v -= (k / m) * y * dt // Euler-Cromer algorithm for numerical solution
    y += v * dt           // Uses new v
    t += dt
  }

  /**
   * Computes the position at the current time using the analytic solution.
   *
   * @return double
   */
  def analyticPosition(): Quantity[Double, Meter] =
    y0 * Math.cos(omega0 * t) + v0 / omega0 * Math.sin(omega0 * t)

  /**
   * Computes the velocity at the current time using the analytic solution.
   *
   * @return double
   */
  def analyticVelocity(): Quantity[Double, Meter / Second] =
    -y0 * omega0 * Math.sin(omega0 * t) + v0 * Math.cos(omega0 * t)
}

object SHOParticle {
  final val k = 1.0.withUnit[Newton / Meter]               // Spring constant
  final val m = 1.0.withUnit[Kilogram] // Assume unit mass
  final val omega0 = java.lang.Math.sqrt(k.value).withUnit[Radian / Second]  // TODO not really being explicit about how we go from sqrt(N / M) to Rad / s
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