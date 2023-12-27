# TypedPhysicsSimulations
This is an experiment in using Scala for physics simulations

# Set-up

You will need to get the Open Source Physics Framework built and add the jar to the project locally.
With sbt, create a lib directory and add the jar there.  To build the jar you currently need to use
the Eclipse IDE to build and export a jar file from the OSP source code:
https://github.com/OpenSourcePhysics

I plan to create a better solution to this if the experiment works out.  Feel free to reach out
for help if you are interested in running my code.


# Project Goals

See if scala 3 can create a compelling environment for physics simulation work.


# Sample

```scala
import org.opensourcephysics.controls.{AbstractAnimation, Animation, OSPControl}
import org.opensourcephysics.display.{DrawingFrame, DrawingPanel, PlottingPanel, Stripchart}
import org.opensourcephysics.display.Circle
import org.opensourcephysics.display.axes.XAxis
import org.opensourcephysics.numerics.{ODE, ODESolver, RK4}

// fundamental coulomb types and methods
import coulomb.*
import coulomb.syntax.*

// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.all.given

// unit and value type policies for operations
import coulomb.policy.standard.given
import scala.language.implicitConversions

// unit definitions
import coulomb.units.si.{*, given}
import coulomb.units.time.Second
import coulomb.units.mks.Newton

class SHOModel extends Circle with ODE {
  val initialState = (0.0.withUnit[Meter], 0.0.withUnit[Meter / Second], 0.0.withUnit[Meter])

  // We need this mutable state which is an array of doubles to work with the ODE interface
  var state:Array[Double] = Array(
    initialState._1.value,
    initialState._2.value,
    initialState._3.value
  )

  val k = 1.withUnit[Newton / Meter]; // spring constant
  val b = 0.2.withUnit[Newton * Second/ Meter] // damping constant

  val ode_solver:ODESolver = new RK4(this)

  def initialize(x:Quantity[Double, Meter], v:Quantity[Double, Meter / Second], t:Quantity[Double, Second]) =
    this.x = x.value
    state(0) = x.value
    state(1) = v.value
    state(2) = t.value

  def getTime(): Double = state(2)

  def getState(): Array[Double] = state

  def getRate(state: Array[Double], rate: Array[Double]): Unit =
    val x = state(0).withUnit[Meter]
    val v = state(1).withUnit[Meter / Second]

    // Here we see the compiler doing dimensional analysis for us
    val force:Quantity[Double, Newton] = -k*x-b*v

    rate(0) = state(1)
    rate(1) = force.value
    rate(2) = 1

  def move(): Unit =
    ode_solver.step()
    setX(state(0))
}

class SHOView extends AbstractAnimation {
  val plot = new PlottingPanel("time", "x", "x(t)")
  val plottingFrame = new DrawingFrame("SHO Data", plot)
  val drawing = new DrawingPanel()
  val drawingFrame = new DrawingFrame("SHO Simulation", drawing)
  val stripChart = new Stripchart(20, 10)
  val sho = new SHOModel()

  drawing.setPreferredMinMax(-5, 5, -1, 1)
  drawing.addDrawable(new XAxis("x"))
  drawing.addDrawable(sho)
  drawingFrame.setSize(300, 150)
  drawingFrame.setVisible(true)
  plot.addDrawable(stripChart)
  plottingFrame.setSize(300, 375)
  plottingFrame.setVisible(true)

  override def initializeAnimation(): Unit =
    super.initializeAnimation()
    val x:Double = control.getDouble("x0")
    val v:Double = control.getDouble("v0")

    sho.initialize(
      x.withUnit[Meter],
      v.withUnit[Meter/Second],
      0.withUnit[Second]
    )
    
    drawing.setMessage("t=0")
    stripChart.clear()
    stripChart.append(0, x)
    drawing.repaint()
    plot.repaint()

  override def doStep(): Unit =
    sho.move()
    stripChart.append(sho.getTime(), sho.getX())
    drawing.setMessage("t="+decimalFormat.format(sho.getTime()))
    drawing.repaint()
    plot.repaint()


  override def resetAnimation(): Unit =
    super.resetAnimation()
    control.setValue("x0", 4)
    control.setValue("v0", 0)
    initializeAnimation()
}

@main
def main(): Unit = {
  println("hello")
  val animation = new SHOView()
  val control = new OSPControl(animation)
  control.addButton("startAnimation", "Start")
  control.addButton("stopAnimation", "Stop")
  control.addButton("initializeAnimation", "Initialize")
  animation.setControl(control)

}
```
