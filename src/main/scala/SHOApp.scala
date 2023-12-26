import org.opensourcephysics.controls.{AbstractAnimation, Animation, OSPControl}
import org.opensourcephysics.display.{DrawingFrame, DrawingPanel, PlottingPanel, Stripchart}
import org.opensourcephysics.display.Circle
import org.opensourcephysics.display.axes.XAxis
import org.opensourcephysics.numerics.{ODE, ODESolver, RK4}

class SHOModel extends Circle with ODE {
  var state = Array[Double](0.0, 0.0, 0.0)

  val k:Double = 1; // spring constant
  val b:Double = 0.2 // damping constant

  val ode_solver:ODESolver = new RK4(this)

  def initialize(x:Double, v:Double, t:Double): Unit =
    this.x = x
    state(0) = x
    state(1) = v
    state(2) = t

  def getTime(): Double =
    state(2)

  def getState(): Array[Double] =
    state

  def getRate(state: Array[Double], rate: Array[Double]): Unit =
    val force:Double = -k*state(0)-b*state(1)
    rate(0) = state(1)
    rate(1) = force
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
    sho.initialize(x,v, 0)
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