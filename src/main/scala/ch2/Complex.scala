/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */


package ch02

class Complex(var real: Double = 0.0, var imag: Double = 0.0) {

  /**
   * Conjugates this complex number.
   * The current complex number is changed.
   */
  def conjugate(): Unit = {
    imag = -imag
  }

  /**
   * Adds a complex number to this complex number and returns the new complex number.
   * The current complex number is not changed.
   *
   * @param c Complex
   * @return Complex
   */
  def add(c: Complex): Complex = {
    new Complex(real + c.real, imag + c.imag)
  }

  /**
   * Multiples this complex number by another complex number and returns the new complex number.
   * The current complex number is not changed.
   *
   * @param c Complex
   * @return Complex
   */
  def multiply(c: Complex): Complex = {
    new Complex(real * c.real - imag * c.imag, real * c.imag + imag * c.real)
  }

  /**
   * Represents this complex number as a string.
   *
   * @return String
   */
  override def toString: String = {
    if (imag >= 0) {
      s"$real + i${Math.abs(imag)}"
    } else {
      s"$real - i${Math.abs(imag)}"
    }
  }
}

/*
 * Open Source Physics software is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public License (GPL) as
 * published by the Free Software Foundation; either version 2 of the License,
 * or(at your option) any later version.
 *
 * Code that uses any portion of the code in the org.opensourcephysics package
 * or any subpackage (subdirectory) of this package must also be released
 * under the GNU GPL license.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307 USA
 * or view the license online at http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2007  The Open Source Physics project
 *                     http://www.opensourcephysics.org
 *
 * Modifications made:
 * [Translated the code to Scala]
 * Copyright (c) [2024] [Daniel Reagan Meyer]
 */