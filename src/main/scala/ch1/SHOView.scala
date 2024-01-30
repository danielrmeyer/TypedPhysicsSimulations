package ch01

import org.opensourcephysics.controls.AbstractAnimation
import org.opensourcephysics.display._
import org.opensourcephysics.display.axes.XAxis

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
    val x = control.getDouble("x0")
    val v = control.getDouble("v0")

    sho.initialize(x, v, 0)
    drawing.setMessage("t=0")
    stripChart.clear()
    stripChart.append(0, x)
    drawing.repaint()
    plot.repaint()
  }

  protected override def doStep(): Unit = {
    sho.move() // moves the particle
    stripChart.append(sho.getTime, sho.getX)
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

