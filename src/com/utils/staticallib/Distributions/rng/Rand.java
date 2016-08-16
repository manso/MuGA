/*
 * Created on Apr 17, 2007
 */
package com.utils.staticallib.Distributions.rng;

import java.util.Random;

import com.utils.staticallib.Distributions.StdUniformRng;


public class Rand implements StdUniformRng {

  Random random;
  
  public Rand() {
    random = new Random();
  }
  
  public void fixupSeeds() {
    ; // do nothing since seeds are managed
  }

  public double random() {
    return random.nextDouble();
  }

}
