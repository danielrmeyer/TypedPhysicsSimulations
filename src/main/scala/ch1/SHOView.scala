/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */


package ch01

import org.opensourcephysics.controls.AbstractAnimation
import org.opensourcephysics.display._
import org.opensourcephysics.display.axes.XAxis
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

class SHOView extends AbstractAnimation {
  val plot: PlottingPanel = new PlottingPanel("time", "x", "x(t)")
  val plottingFrame: DrawingFrame = new DrawingFrame("SHO Data", plot)
  val drawing: DrawingPanel = new DrawingPanel()
  val drawingFrame: DrawingFrame = new DrawingFrame("SHO Simulation", drawing)
  val stripChart: Dataset = new Stripchart(20, 10) // strip chart of x(t)
  val sho: SHOModel = new SHOModel()               // harmonic oscillator

  // This is the primary constructor
  drawing.setPreferredMinMax(-5, 5, -1, 1)
  drawing.addDrawable(new XAxis("x"))
  drawing.addDrawable(sho)
  drawingFrame.setSize(300, 150)
  drawingFrame.setVisible(true)
  plot.addDrawable(stripChart)
  plottingFrame.setSize(300, 375)
  plottingFrame.setVisible(true)

  override def initializeAnimation(): Unit = {
    super.initializeAnimation()
    val x = control.getDouble("x0").withUnit[Meter]
    val v = control.getDouble("v0").withUnit[Meter / Second]

    sho.initialize(x, v, 0.withUnit[Second])
    drawing.setMessage("t=0")
    stripChart.clear()
    stripChart.append(0, x.value)
    drawing.repaint()
    plot.repaint()
  }

  protected override def doStep(): Unit = {
    sho.move() // moves the particle
    stripChart.append(sho.getTime.value, sho.getX)
    drawing.setMessage(s"t=${decimalFormat.format(sho.getTime)}")
    drawing.repaint()
    plot.repaint()
  }

  override def resetAnimation(): Unit = {
    super.resetAnimation
    control.setValue("x0", 4)
    control.setValue("v0", 0)

    initializeAnimation

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