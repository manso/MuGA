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

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created on 3/out/2015, 20:08:51
 *
 * @author zulu - computer
 */
public class DateUtils {

    public static String difTime(Date date1, Date date2) {
        long diff = date2.getTime() - date1.getTime();
        long dmilis = diff % 1000;
        long dsecs = (diff / 1000)%60;
        long dminutes = (diff / (60 * 1000)) %60;
        long dhours = (diff / (60 * 60 * 1000)) %24;
        long ddays = diff / (24 * 60 * 60 * 1000);
        return String.format("%d %02d:%02d:%02d.%03d", ddays,dhours,dminutes,dsecs,dmilis);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510032008L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
