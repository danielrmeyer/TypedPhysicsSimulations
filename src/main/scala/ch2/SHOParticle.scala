/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */
package ch02

class SHOParticle(initialY: Double, initialV: Double) extends Particle {
  import SHOParticle._

  println("A new SHO object is being created.")
  y = initialY
  v = initialV

  private var y0: Double = y
  private var v0: Double = v

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
  def analyticPosition(): Double = y0 * Math.cos(omega0 * t) + v0 / omega0 * Math.sin(omega0 * t)

  /**
   * Computes the velocity at the current time using the analytic solution.
   *
   * @return double
   */
  def analyticVelocity(): Double = -y0 * omega0 * Math.sin(omega0 * t) + v0 * Math.cos(omega0 * t)
}

object SHOParticle {
  final val k: Double = 1.0               // Spring constant
  final val m: Double = 1.0
  final val omega0: Double = Math.sqrt(k) // Assume unit mass
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