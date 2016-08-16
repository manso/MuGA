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
//::                                                               (c)2015   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package com.utils;

import com.evolutionary.Genetic;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created on 5/out/2015, 6:32:00
 *
 * @author zulu - computer
 */
public class MyString {

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static final String COMMENT = "#";
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
    public static String LINE = COMMENT + "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::";

    /**
     * Align the string to the left inserting spaces in the end
     *
     * @param text string to align
     * @param len lenght of the string
     * @return string with spaces in the end
     */
    public static String setSize(String text, int len) {
        return String.format("%-" + len + "s", text);
//        String str = String.format("%-" + len + "s", text);
//        str = str.replaceAll(" ",".");
//        return str;
    }

    /**
     * Align the string to right inserting spaces in the beginning
     *
     * @param text string to align
     * @param len lenght of the string
     * @return string with spaces in the beginning
     */
    public static String align(String text, int len) {
        return String.format("%" + len + "s", text);
    }

    /**
     * Align a number to the right
     *
     * @param v number
     * @param len lenght of the string
     * @return string with spaces in the beginning
     */
    public static String align(double v, int len) {
        return MyNumber.numberToString(v, len);
    }

    /**
     * Align a number to the center
     *
     * @param text text to align
     * @param len lenght of the string
     * @return string in the center
     */
    public static String center(String text, int len) {
        String out = String.format("%" + len + "s%s%" + len + "s", "", text, "");
        float mid = (out.length() / 2);
        float start = mid - (len / 2);
        float end = start + len;
        return out.substring((int) start, (int) end);
    }

    /**
     * Align a number to the center
     *
     * @param text text to align
     * @param len lenght of the string
     * @return string in the center
     */
    public static String center(String text, int len, char fill) {
        StringBuilder out = new StringBuilder(center(text, len));
        for (int i = 0; i < out.length() && out.charAt(i) == ' '; i++) {
            out.setCharAt(i, fill);
        }
        for (int i = out.length() - 1; i >= 0 && out.charAt(i) == ' '; i--) {
            out.setCharAt(i, fill);
        }
        return out.toString();
    }

    public static String toComment(String original) {
        return COMMENT + original.replaceAll("\n", "\n" + COMMENT);
    }

    public static String toString(Date d) {
        return dateFormat.format(d);
    }

    public static String toFileString(String txt) {
        return txt.replaceAll("\n", "\r\n");
    }

    public static String getCopyright() {
        return "\n Build at " + MyString.toString(new Date()) + " - " + Genetic.VERSION;
    }

    public static String[] splitByWhite(String txt) {
        return txt.trim().split("\\s+");
    }

    public static String insertRegularSpaces(String txt, int kay) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < txt.length(); i++) {
            if (i > 0 && i % kay == 0) {
                str.append(" ");
            }
            str.append(txt.charAt(i));
        }
        return str.toString();
    }

    /**
     * remove characters in the string there are not '0' or '1'
     *
     * @param bits
     * @return
     */
    public static String removeNot01(String bits) {
        StringBuffer txt = new StringBuffer();
        for (char c : bits.toCharArray()) {
            if (c == '0' || c == '1') {
                txt.append(c);
            }
        }
        return txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510050632L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
