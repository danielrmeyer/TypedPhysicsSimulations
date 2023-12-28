package numerics

trait ODESolver {
  def initialize(stepSize: Double): Unit
  
  def step(): Double
  
  def setStepSize(stepSize:Double): Unit
  
  def getStepSize(): Double
  
}