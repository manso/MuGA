//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package com.utils;

import java.util.Random;

/**
 * Created on 23/mar/2016, 18:29:07
 *
 * @author zulu - computer
 */
public class ThreadSafeRandom extends Random{

    // same constants as Random, but must be redeclared because private
    private final  long multiplier = 0x5DEECE66DL;
    private final  long addend = 0xBL;
    private final  long mask = (1L << 48) - 1;
    private static final double DOUBLE_UNIT = 0x1.0p-53; // 1.0 / (1L << 53)

    /**
     * The random seed. We can't use super.seed
     */
    private long rnd;

    /**
     * Initialization flag to permit the first and only allowed call to setSeed
     * (inside Random constructor) to succeed. We can't allow others since it
     * would cause setting seed in one part of a program to unintentionally
     * impact other usages by the thread.
     */
    boolean initialized;

    // Padding to help avoid memory contention among seed updates in
    // different TLRs in the common case that they are located near
    // each other.
    private long pad0, pad1, pad2, pad3, pad4, pad5, pad6, pad7;

   

    /**
     * Constructor called only by localRandom.initialValue. We rely on the fact
     * that the superclass no-arg constructor invokes setSeed exactly once to
     * initialize.
     */
   public  ThreadSafeRandom() {
        super();
    }

   
    /**
     * Throws UnsupportedOperationException. Setting seeds in this generator is
     * unsupported.
     *
     * @throws UnsupportedOperationException always
     */
    public void setSeed(long seed) {
//        if (initialized) {
//            throw new UnsupportedOperationException();
//        }
        initialized = true;
        rnd = (seed ^ multiplier) & mask;
    }

    protected int next(int bits) {
        return (int) ((rnd = (rnd * multiplier + addend) & mask) >>> (48 - bits));
    }
  

    /**
     * Returns a pseudorandom, uniformly distributed value between the given
     * least value (inclusive) and bound (exclusive).
     *
     * @param least the least value returned
     * @param bound the upper bound (exclusive)
     * @throws IllegalArgumentException if least greater than or equal to bound
     * @return the next value
     */
    public int nextInt(int least, int bound) {
        if (least >= bound) {
            throw new IllegalArgumentException();
        }
        return next(bound - least) + least;
    }

    /**
     * Returns a pseudorandom, uniformly distributed value between 0 (inclusive)
     * and the specified value (exclusive)
     *
     * @param n the bound on the random number to be returned. Must be positive.
     * @return the next value
     * @throws IllegalArgumentException if n is not positive
     */
    public long nextLong(long n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        // Divide n by two until small enough for nextInt. On each
        // iteration (at most 31 of them but usually much less),
        // randomly choose both whether to include high bit in result
        // (offset) and whether to continue with the lower vs upper
        // half (which makes a difference only if odd).
        long offset = 0;
        while (n >= Integer.MAX_VALUE) {
            int bits = next(2);
            long half = n >>> 1;
            long nextn = ((bits & 2) == 0) ? half : n - half;
            if ((bits & 1) == 0) {
                offset += n - nextn;
            }
            n = nextn;
        }
        return offset + next((int) n);
    }

    /**
     * Returns a pseudorandom, uniformly distributed value between the given
     * least value (inclusive) and bound (exclusive).
     *
     * @param least the least value returned
     * @param bound the upper bound (exclusive)
     * @return the next value
     * @throws IllegalArgumentException if least greater than or equal to bound
     */
    public long nextLong(long least, long bound) {
        if (least >= bound) {
            throw new IllegalArgumentException();
        }
        return nextLong(bound - least) + least;
    }

    /**
     * Returns a pseudorandom, uniformly distributed {@code double} value
     * between 0 (inclusive) and the specified value (exclusive)
     *
     * @param n the bound on the random number to be returned. Must be positive.
     * @return the next value
     * @throws IllegalArgumentException if n is not positive
     */
    public double nextDouble(double n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        return nextDouble() * n;
    }

    /**
     * Returns a pseudorandom, uniformly distributed value between the given
     * least value (inclusive) and bound (exclusive).
     *
     * @param least the least value returned
     * @param bound the upper bound (exclusive)
     * @return the next value
     * @throws IllegalArgumentException if least greater than or equal to bound
     */
    public double nextDouble(double least, double bound) {
        if (least >= bound) {
            throw new IllegalArgumentException();
        }
        return nextDouble() * (bound - least) + least;
    }
    
     public double nextDouble() {
        return (((long)(next(26)) << 27) + next(27)) * DOUBLE_UNIT;
    }

}
