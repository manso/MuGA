/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.util.Date;

/**
 *
 * @author zulu
 */
public class MyMath {

    public static int log2(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    public static int log(int x, int base) {
        return (int) (Math.log(x) / Math.log(base));
    }

    public static long pow2(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        return (long) Math.pow(2, n);
    }

    /**
     * Get a diff between two dates
     *
     * @param date1 the oldest date
     * @param date2 the newest date
     * @return the diff value
     */
    public static Date getDateDiff(Date date1, Date date2) {
        return new Date(date2.getTime() - date1.getTime());
    }

}
