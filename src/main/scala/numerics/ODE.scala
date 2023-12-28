package numerics

trait ODE {
  
  
  def getState(): Array[Double]
  
  
  def getRate(state:Array[Double]): Array[Double]
  
}