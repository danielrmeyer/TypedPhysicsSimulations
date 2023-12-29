# Typed Physics Simulations
This is an experiment in using Scala for physics simulations.  The Open Source Physics Library is being
used for core functionality.

The examples are motivated by the book "Introduction to Computer Simulation Methods" by Harvey Gould, Jan Tobochnik, and Wolfgang Christian.

# Project Goals

See if scala 3 can create a compelling environment for physics simulation work.


# Sample Physics Simulation

In the sample code below we are simulating the damped simple harmonic oscillator using scala 3.  Physical units are
no longer just Doubles but now have proper physical dimensions thanks to Coulomb, a units of measure library.  The compiler is now
doing dimensional analysis. To make this work I had to rewrite RK4.java nd ODE.java.  Those have been
replaced by ODE.scala and RK4.scala under the numerics package.  The entire process now forces the dimensions
to make sense from beginning to end.

```scala

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

```

# How to run the code

Right now the code is organized as a series of apps under src/main/scala.  The simplest way to
run an example app is to use the repl:
`sbt console`
Load in the app code:
`:load src/main/scala/SHOApp.scala`
And execute the main function:
`sho()`
Running the main methods from the console ensures sbt is in interactive mode and avoids
immediate hang-up by sbt.

You need to install sbt (simple build tool) and cd to the TypedPhysicsSimulations directory.
