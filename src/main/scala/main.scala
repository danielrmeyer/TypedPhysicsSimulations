import org.opensourcephysics.display._
import org.opensourcephysics.frames._
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE

@main
def main(): Unit = {
  println("Hello world!")
  val frame = new PlotFrame("position", "amplitude", "First Plot")
  frame.setSize(400, 400)

  val xStart = BigDecimal(-10)
  val xEnd = BigDecimal(10)
  val xIncrement = BigDecimal(0.1)

  for( x <- xStart to xEnd by xIncrement)
    frame.append(0, x.toDouble, Math.sin(x.toDouble))

  frame.setVisible(true)
  frame.setDefaultCloseOperation(EXIT_ON_CLOSE)
}