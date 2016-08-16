/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import static com.utils.MyStatistics.meansAreEquals;
import static com.utils.MyStatistics.p_values;
import static com.utils.MyStatistics.rank;
import java.util.Locale;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.utils.MyStatistics.compareMeansTtest;

/**
 *
 * @author zulu
 */
public class MyStatisticsTest {

    /**
     * Test of std method, of class MyStatistics.
     */
    @Test
    public void testStd() {
        //https://martinsdeveloperworld.wordpress.com/2015/07/30/evaluating-performance-measurements-with-apaches-commons-math3/
        double[] x = {9, 2, 5, 4, 12, 7, 8, 11, 9, 3, 7, 4, 12, 5, 4, 10, 9, 6, 9, 4};
        double[] sample1 = {154.3, 191.0, 163.4, 168.6, 187.0, 200.4, 162.5};
        double[] sample2 = {230.4, 202.8, 202.8, 216.8, 192.9, 194.4, 211.7};
        double[] sample3 = {154.3, 191.0, 163.4, 168.6, 187.0, 200.4, 162.5};
        double[] sample4 = {155.3, 163.7, 200.1, 177.5, 188.3, 198.7, 201.7};

        double[][] test = {
            sample1,
            sample2,
            sample3,
            sample4
        };
        String label[] = {"s1", "s2", "s3", "s4"};
        System.out.println("\nP_Values");
        double[][] p = p_values(test);
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p.length; j++) {
                System.out.printf(Locale.US, " %4.4f", p[i][j]);
            }
            System.out.println();
        }
        System.out.println("\nT_Test 95%");
        boolean[][] d = meansAreEquals(test, 0.05);
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p.length; j++) {
                System.out.print(" " + (d[i][j] ? "1" : "0"));
            }
            System.out.println();
        }

        System.out.println("\nResultt 95%");
        char[][] s = compareMeansTtest(test, 0.05, true);
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p.length; j++) {
                System.out.print(" " + s[i][j]);
            }
            System.out.println();
        }

        System.out.println("\nRank 95%");
        int[] r = rank(test, 0.05, true);
        for (int i = 0; i < p.length; i++) {
            System.out.print(" " + r[i]);
        }
        System.out.println("\nRank Labels 95%");
        Ranking.sortByRank(label, r);
        for (int i = 0; i < label.length; i++) {
            System.out.println(r[i] + " = " + label[i]);

        }
    }

}
