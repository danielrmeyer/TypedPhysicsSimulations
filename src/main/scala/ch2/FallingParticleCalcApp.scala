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

import org.opensourcephysics.controls._

class FallingParticleCalcApp extends AbstractCalculation {

  def calculate(): Unit = {
    // Gets initial conditions
    val y0 = control.getDouble("Initial y").withUnit[Meter]
    val v0 = control.getDouble("Initial v").withUnit[Meter / Second]

    // Sets initial conditions
    val ball = new FallingParticle(y0, v0)

    // Reads parameters and sets dt
    ball.dt = control.getDouble("dt").withUnit[Second]
    while (ball.y > 0.0.withUnit[Meter]) {
      ball.step()
    }

    // Displays numerical results
    control.println(s"final time = ${ball.t.show}")
    control.println(s"y = ${ball.y} v = ${ball.v.show}")
    control.println(s"analytic y = ${ball.analyticPosition().show}")
    control.println(s"analytic v = ${ball.analyticVelocity().show}")
  }

  override def reset(): Unit = {
    control.setValue("Initial y", 10)
    control.setValue("Initial v", 0)
    control.setValue("dt", 0.01)
  }
}

object FallingParticleCalcApp {
  def main(args: Array[String]): Unit = {
    CalculationControl.createApp(new FallingParticleCalcApp())
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
 * [Added Unit Awareness]
 * Copyright (c) [2024] [Daniel Reagan Meyer]
 */