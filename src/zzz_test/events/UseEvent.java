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
package zzz_test.events;

/**
 * Created on 14/mar/2016, 4:50:26
 *
 * @author zulu - computer
 */
public class UseEvent {

    public static void main(String[] args) {
// Create a copy of the demonstration class.
        DemoClass TestClass = new DemoClass();
// Value will start at 0, so keep adding 1
// until Value should equal 5.
        TestClass.AddOne();
        System.out.println(TestClass.getValue());
        TestClass.AddOne();
        System.out.println(TestClass.getValue());
        TestClass.AddOne();
        System.out.println(TestClass.getValue());
        TestClass.AddOne();
        System.out.println(TestClass.getValue());
        TestClass.AddOne();
        System.out.println(TestClass.getValue());
// Now that Value is at 4 (we couldn't make it
// equal 5), keep subtracting 1 until Value
// should equal -1.
        TestClass.SubtractOne();
        System.out.println(TestClass.getValue());
        TestClass.SubtractOne();
        System.out.println(TestClass.getValue());
        TestClass.SubtractOne();
        System.out.println(TestClass.getValue());
        TestClass.SubtractOne();
        System.out.println(TestClass.getValue());
        TestClass.SubtractOne();
        System.out.println(TestClass.getValue());
// Try setting the value directly.
        TestClass.setValue(3);
        System.out.println(TestClass.getValue());
        TestClass.setValue(5);
        System.out.println(TestClass.getValue());
        TestClass.setValue(-5);
        System.out.println(TestClass.getValue());
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603140450L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
