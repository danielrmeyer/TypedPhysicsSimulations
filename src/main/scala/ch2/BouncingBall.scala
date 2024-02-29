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
import org.opensourcephysics.controls.*
import org.opensourcephysics.frames.*
// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.all.{*, given}
// unit and value type policies for operations
import coulomb.policy.standard.given

import scala.language.implicitConversions
// unit definitions
import coulomb.units.mks.{Meter, Second}
import org.opensourcephysics.display.Circle

/**
 * BouncingBall models a falling ball as it bounces off of a floor or a wall.
 */

import org.opensourcephysics.display.Circle

class BouncingBall(
                    initialX: Quantity[Double, Meter],
                    var vx: Quantity[Double, Meter / Second],
                    initialY: Quantity[Double, Meter],
                    var vy: Quantity[Double, Meter / Second]
                  ) extends Circle {

  import BouncingBall.*

  // Initialize position using Circle's method
  setXY(initialX.value, initialY.value)

  def step(dt: Quantity[Double, Second]): Unit =
    val newX = getX.withUnit[Meter] + vx * dt
    val newY = getY.withUnit[Meter] + vy * dt
    vy -= g * dt

    if (newX > WALL)
      vx = -Math.abs(vx.value).withUnit[Meter / Second]
    else if (newX < -WALL) {
      vx = Math.abs(vx.value).withUnit[Meter / Second]
    }
    if (newY < 0.withUnit[Meter])
      vy = Math.abs(vy.value).withUnit[Meter / Second]

    // Update position using Circle's method
    setXY(newX.value, newY.value)
}

object BouncingBall {
  final val g = 9.8.withUnit[Meter / (Second ^ 2)]
  final val WALL = 10.withUnit[Meter]
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