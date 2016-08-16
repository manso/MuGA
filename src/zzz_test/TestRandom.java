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

package zzz_test;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 23/mar/2016, 18:42:52 
 * @author zulu - computer
 */
public class TestRandom  implements Runnable{
    Random rnd; 
    long finalrnd;
    public TestRandom(long seed) {
        rnd = new Random(seed);
    }


    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201603231842L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                finalrnd = rnd.nextLong();
                Thread.sleep(rnd.nextInt(15));
            } catch (InterruptedException ex) {
                Logger.getLogger(TestRandom.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        System.out.println(Thread.currentThread().getName() + " " + finalrnd);
    }
    
    public static void main(String[] args) {
        
        for (int i = 0; i <10; i++) {
            new Thread(new TestRandom((i+5)*1234)).start();            
        }
    }
}