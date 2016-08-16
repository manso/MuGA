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

import java.util.Locale;

/**
 * Created on 19/jan/2016, 12:33:05
 *
 * @author zulu - computer
 */
public class MyNumber {

    /**
     * convert double to string with size characters
     *
     * @param value double number
     * @param size number characters
     * @return string with number and white spaces if necessary
     */
    public static String numberToString(double value, int size) {
        int val = (int) value;
        if (val == value) {
            return IntegerToString(val, size);
        } else {
            return DoubleToString(value, size);
        }
    }

    /**
     * convert integer to string with size characters
     *
     * @param value number integer
     * @param size number of characters
     * @return string with number and white spaces if necessary
     */
    public static String IntegerToString(int value, int size) {
        StringBuffer str = new StringBuffer(value + "");
        StringBuilder tmp = new StringBuilder(str);
        while (tmp.length() < size) {
            tmp.insert(0, " ");
        }
        return tmp.substring(0, size);
    }

    /**
     * convert double to string with size characters
     *
     * @param value double number
     * @param size number characters
     * @return string with number and white spaces if necessary
     */
    public static String DoubleToString(double value, int size) {
        StringBuilder number = new StringBuilder(String.format(Locale.US, "%f",value));
        int indexOfE = number.indexOf("E");
        if (number.length() < size || indexOfE == -1) {
            return MyString.align(number.toString(), size);
        }
        int excess = number.length() - size;
        int start = indexOfE - excess > 0 ? indexOfE - excess : 0;
        number = number.delete(start, indexOfE);
        return number.toString();
    }

    /**
     * set the string with size lenght
     *
     * @param str string original
     * @param size new size
     * @return string cuted or with white spaces
     */
    public static String getStringSize(String str, int size, char space) {

        StringBuilder tmp = new StringBuilder();
        int index = 0;
        while (tmp.length() < size && tmp.length() < str.length()) {
            tmp.append(str.charAt(index++));
        }
        while (tmp.length() < size) {
            tmp.append(space);
        }
        return tmp.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201601191233L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
