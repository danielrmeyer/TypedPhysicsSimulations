package ch01

import org.opensourcephysics.controls.{Animation, OSPControl}

object SHOApp {
  /**
   * Starts the Scala application.
   * @param args command line parameters
   */
  def main(args: Array[String]): Unit = {
    val animation: Animation = new SHOView()
    val control: OSPControl = new OSPControl(animation)
    control.addButton("startAnimation", "Start")
    control.addButton("stopAnimation", "Stop")
    control.addButton("initializeAnimation", "Initialize")
    animation.setControl(control)
  }
}
