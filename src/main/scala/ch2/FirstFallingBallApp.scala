/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

package ch02

object FirstFallingBallApp {

  def main(args: Array[String]): Unit = {
    val y0 = 10.0   // Initial position
    val v0 = 0.0    // Initial velocity
    var t = 0.0     // Time
    val dt = 0.01   // Time step
    var y = y0
    var v = v0
    val g = 9.8     // Gravitational field

    for (n <- 0 until 100) {
      y += v * dt
      v -= g * dt
      t += dt
    }

    println("Results")
    println(s"final time = $t")
    // Display numerical result
    println(s"y = $y v = $v")
    // Display analytic result
    val yAnalytic = y0 + v0 * t - 0.5 * g * t * t
    val vAnalytic = v0 - g * t
    println(s"analytic y = $yAnalytic v = $vAnalytic")
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