/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */


package ch02

import org.opensourcephysics.controls.*
import org.opensourcephysics.frames.*

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

import numerics.Math

class BouncingBallApp extends AbstractSimulation {
  val frame: DisplayFrame = new DisplayFrame("x", "y", "Bouncing Balls")
  var balls: Array[BouncingBall] = _
  var time: Quantity[Double, Second] = _
  var dt: Quantity[Double, Second] = _

  override def initialize(): Unit =
    frame.setPreferredMinMax(-10.0, 10.0, 0, 10)
    time = 0.withUnit[Second]
    frame.clearDrawables()
    val n = control.getInt("number of balls")
    val v = control.getInt("speed").toFloat.withUnit[Meter / Second]
    balls = new Array[BouncingBall](n)
    for (i <- 0 until n)
      val theta = Math.PI * scala.math.random()
      balls(i) = new BouncingBall(
        0.withUnit[Meter], 
        v * Math.cos(theta),
        0.withUnit[Meter], 
        v * Math.sin(theta)
      )
      
      frame.addDrawable(balls(i))


    frame.setMessage("t = " + decimalFormat.format(time))


  override def doStep(): Unit =
    balls.foreach(_.step(dt))
    time += dt
    frame.setMessage("t=" + decimalFormat.format(time))

  override def startRunning(): Unit =
    dt = control.getDouble("dt").withUnit[Second]

  override def reset(): Unit =
    control.setAdjustableValue("dt", 0.1)
    control.setValue("number of balls", 40)
    control.setValue("speed", 10)
}

object BouncingBallApp {
  def main(args: Array[String]): Unit =
    SimulationControl.createApp(new BouncingBallApp())
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