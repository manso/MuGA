///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2013                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso                             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****************************************************************************/
///****************************************************************************/
///****     This software was built with the purpose of investigating      ****/
///****     and learning. Its use is free and is not provided any          ****/
///****     guarantee or support.                                          ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package GUI.utils;

import com.evolutionary.Genetic;

/**
 *
 * @author Zulu
 */
public class MuGAObject {

    public Genetic obj; //object of class

    public MuGAObject(String name) throws ClassNotFoundException {
        try {
            this.obj = (Genetic)Class.forName(name).newInstance();
        } catch (Exception ex) {
            this.obj = null;
        }
    }

    @Override
    public String toString() {
        return obj.getClass().getSimpleName();
    }

    public Genetic getObject() {
        return obj;
    } 
    public String getClassName() {
        return obj.getClass().getCanonicalName();
    } 
     public String getName() {
        return obj.getClass().getSimpleName();
    } 

}
