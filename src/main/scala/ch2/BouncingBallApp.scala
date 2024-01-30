/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */


package ch02

import org.opensourcephysics.controls._
import org.opensourcephysics.frames._

class BouncingBallApp extends AbstractSimulation {
  val frame: DisplayFrame = new DisplayFrame("x", "y", "Bouncing Balls")
  var ball: Array[BouncingBall] = _
  var time: Double = _
  var dt: Double = _

  override def initialize(): Unit = {
    frame.setPreferredMinMax(-10.0, 10.0, 0, 10)
    time = 0
    frame.clearDrawables()
    val n = control.getInt("number of balls")
    val v = control.getInt("speed")
    ball = new Array[BouncingBall](n)
    for (i <- 0 until n) {
      val theta = Math.PI * Math.random()
      ball(i) = new BouncingBall(0, v * Math.cos(theta), 0, v * Math.sin(theta))
      frame.addDrawable(ball(i))
    }
    frame.setMessage("t = " + decimalFormat.format(time))
  }

  override def doStep(): Unit = {
    ball.foreach(_.step(dt))
    time += dt
    frame.setMessage("t=" + decimalFormat.format(time))
  }

  override def startRunning(): Unit = {
    dt = control.getDouble("dt")
  }

  override def reset(): Unit = {
    control.setAdjustableValue("dt", 0.1)
    control.setValue("number of balls", 40)
    control.setValue("speed", 10)
  }
}

object BouncingBallApp {
  def main(args: Array[String]): Unit = {
    SimulationControl.createApp(new BouncingBallApp())
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