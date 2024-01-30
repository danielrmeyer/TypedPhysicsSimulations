/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

package ch02

import org.opensourcephysics.display.Circle

/**
 * BouncingBall models a falling ball as it bounces off of a floor or a wall.
 */

import org.opensourcephysics.display.Circle

class BouncingBall(initialX: Double, var vx: Double, initialY: Double, var vy: Double) extends Circle {

  import BouncingBall._

  // Initialize position using Circle's method
  setXY(initialX, initialY)

  def step(dt: Double): Unit = {
    val newX = getX + vx * dt
    val newY = getY + vy * dt
    vy -= g * dt

    if (newX > WALL) {
      vx = -Math.abs(vx)
    } else if (newX < -WALL) {
      vx = Math.abs(vx)
    }
    if (newY < 0) {
      vy = Math.abs(vy)
    }

    // Update position using Circle's method
    setXY(newX, newY)
  }
}

object BouncingBall {
  final val g = 9.8
  final val WALL = 10
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