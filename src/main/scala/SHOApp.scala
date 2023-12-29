
import org.opensourcephysics.controls.{AbstractAnimation, OSPControl}
import org.opensourcephysics.display.*
import org.opensourcephysics.display.axes.XAxis
import numerics.{ODE, RK4}

// fundamental coulomb types and methods
import coulomb.*
import coulomb.syntax.*

// algebraic definitions
import algebra.instances.all.given

// unit and value type policies for operations
import coulomb.policy.standard.given

import scala.language.implicitConversions

// unit definitions
import coulomb.units.mks.{Meter, Newton, Second, Kilogram}


import numerics.State

class SHOModel extends Circle with ODE {

  var state = State(0.withUnit[Meter], 0.withUnit[Meter / Second], 0.withUnit[Second])

  val k = 1.withUnit[Newton / Meter]; // spring constant
  val b = 0.2.withUnit[Newton * Second / Meter] // damping constant
  val ode_solver = new RK4(this)

  def getTime(): Quantity[Double, Second] = state.t

  // ODE interface
  def getState() = state

  // ODE interface
  def getRate(state: State): State =
    val x = state.x
    val v = state.v
    //Here we see the compiler do dimensional analysis for us
    val force: Quantity[Double, Newton] = -k * x - b * v

    val rate = force / 1.withUnit[Kilogram]

    State(v * 1.0.withUnit[Second], rate * 1.0.withUnit[Second], 1.withUnit[Second])


  def initialize(
                  x: Quantity[Double, Meter],
                  v: Quantity[Double, Meter / Second],
                  t: Quantity[Double, Second]
                ): Unit =
    setX(x.value)

    this.state = State(x, v, t)

  def move(): Unit =
    this.state = ode_solver.step(this.state)
    setX(this.state.x.value)
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
    val x: Double = control.getDouble("x0")
    val v: Double = control.getDouble("v0")

    sho.initialize(
      x.withUnit[Meter],
      v.withUnit[Meter / Second],
      0.withUnit[Second]
    )

    drawing.setMessage("t=0")
    stripChart.clear()
    stripChart.append(0, x)
    drawing.repaint()
    plot.repaint()

  override def doStep(): Unit =
    sho.move()
    stripChart.append(sho.getTime().value, sho.getX())
    drawing.setMessage("t=" + decimalFormat.format(sho.getTime()))
    drawing.repaint()
    plot.repaint()


  override def resetAnimation(): Unit =
    super.resetAnimation()
    control.setValue("x0", 4)
    control.setValue("v0", 0)
    initializeAnimation()
}

@main
def sho(): Unit = {
  val animation = new SHOView()
  val control = new OSPControl(animation)
  control.addButton("startAnimation", "Start")
  control.addButton("stopAnimation", "Stop")
  control.addButton("initializeAnimation", "Initialize")
  animation.setControl(control)

}