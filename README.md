# Typed Physics Simulations
This is an experiment in using Scala for physics simulations.  The Open Source Physics Library is being
used for core functionality.

The examples are motivated by the book "Introduction to Computer Simulation Methods" by Harvey Gould, Jan Tobochnik, and Wolfgang Christian.

# Project Goals

See if scala 3 can create a compelling environment for physics simulation work.


# Sample Physics Simulation

Below is a taste of how this looks.  Physical units are
no longer just Doubles but now have proper physical dimensions thanks to Coulomb, a units of measure library.  The compiler is now
doing dimensional analysis. 

Also implemented is a simple harmonic oscillator simulation.  To make that work I had to rewrite RK4.java nd ODE.java.  Those have been
replaced by ODE.scala and RK4.scala under the numerics package.  The entire process now forces the dimensions
to make sense from beginning to end.

```scala

import coulomb.*
import coulomb.syntax.*

// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.all.{*, given}

// unit and value type policies for operations
import coulomb.policy.standard.given
import scala.language.implicitConversions

// unit definitions
import coulomb.units.mks.{Meter, Second}


@main
def firstFallingBallApp() =
  val y0 = 10.0.withUnit[Meter]
  val v0 = 0.0.withUnit[Meter / Second]

  val dt = 0.01.withUnit[Second]
  val g = 9.8.withUnit[Meter / (Second ^ 2)]

  var y = y0
  var v = v0
  var t = 0.0.withUnit[Second]

  for(n <- 0 to 100)
    y = y + v*dt
    v = v - dt*g
    t = t + dt

  println("Results")
  println("final time = " + t.show)
  println("y = "+y.show+" v = " + v.show)
  
  val yAnalytic = y0 + v0 * t - 0.5 * g * t * t
  val vAnalytic = v0 - g * t
  System.out.println("analytic y = " + yAnalytic.show + " v = " + vAnalytic.show)

```
Running the above will give you the following output:
```bash
Results
final time = 1.0100000000000007 s
y = 5.051000000000003 m v = -9.898000000000007 m/s
analytic y = 5.0015099999999935 m v = -9.898000000000007 m/s
```

# How to run the code

Right now the code is organized as a series of apps under src/main/scala.  The simplest way to
run an example app is to use the repl:
`sbt console`
Load in the app code and run the main method:
```bash
sbt console
                                                                                
scala> :load src/main/scala/FirstFallingBallApp.scala
def firstFallingBallApp(): Unit
                                                                                
scala> firstFallingBallApp()
Results
final time = 1.0100000000000007 s
y = 5.051000000000003 m v = -9.898000000000007 m/s
analytic y = 5.0015099999999935 m v = -9.898000000000007 m/s
                                                                                
scala> 

```

To run the SHO simulations do the following:
```bash
scala> :load src/main/scala/SHOApp.scala
// defined class SHOModel
// defined class SHOView
def shoApp(): Unit
                                                                                
scala> shoApp()

```
Running the main methods from the console ensures sbt is in interactive mode and avoids
immediate hang-up by sbt.

You need to install sbt (simple build tool) and cd to the TypedPhysicsSimulations directory.
